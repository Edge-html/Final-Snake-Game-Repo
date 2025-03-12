package com.snake.snakes2.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.google.firebase.firestore.FirebaseFirestore
import com.snake.snakes2.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border


@Composable
fun GameRoomScreen(navController: NavController, gameId: String, username: String) {
    val db = FirebaseFirestore.getInstance()
    var players by remember { mutableStateOf(listOf<String>()) }

    // Fetch players when the screen is composed
    LaunchedEffect(key1 = gameId) {
        db.collection("game-session").document(gameId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val playerList = snapshot.get("players") as? List<String> ?: emptyList()
                    players = playerList
                }
            }
    }

    val customFont = FontFamily(Font(R.font.score_font, FontWeight.Bold))

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.snakedash),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Game Room content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Game Room for Game ID: $gameId",
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = customFont
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Host: $username",
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = customFont
            )
            Text(
                "Players in this room:",
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = customFont
            )
            players.forEach { player ->
                Text(
                    player,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Start Game button
            HomeButton("Start Game") {
                navController.navigate("multiplayerArena/$gameId/$username")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Leave Room button
            HomeButton("Leave Room") {
                navController.navigateUp()
            }
        }
    }
}


