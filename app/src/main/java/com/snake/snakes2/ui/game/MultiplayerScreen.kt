//MultiplayerScreen.kt
package com.snake.snakes2.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.snake.snakes2.R
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun MultiplayerScreen(navController: NavController, username: String) {
    val db = FirebaseFirestore.getInstance()

    fun createGameSession() {
        val newGame = hashMapOf(
            "hostUsername" to username,
            "players" to listOf(username),
            "state" to "waiting for players"
        )
        db.collection("game-session").add(newGame).addOnSuccessListener { documentReference ->
            navController.navigate("gameRoom/${documentReference.id}/$username")
        }.addOnFailureListener {
            // Handle errors
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xff242c11))) {
        Image(
            painter = painterResource(id = R.drawable.snakedash),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Multiplayer Hub",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            HomeButton("Create Game") { createGameSession() }
            HomeButton("Join Game") { navController.navigate("joinGameScreen/$username") }
            HomeButton("Back to Home") { navController.navigate("homeScreen/$username") }
        }
    }
}

