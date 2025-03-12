package com.snake.snakes2.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.snake.snakes2.R

@Composable
fun MultiplayerArena(navController: NavController, gameId: String, username: String) {
    val db = FirebaseFirestore.getInstance()
    var gameSession by remember { mutableStateOf<Map<String, Any>?>(null) }

    LaunchedEffect(key1 = gameId) {
        db.collection("game-session").document(gameId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    gameSession = document.data
                }
            }
    }

    val customFont = FontFamily(Font(R.font.score_font, FontWeight.Bold))

    Box(modifier = Modifier.fillMaxSize().background(Color(0xff242c11))) {
        Image(
            painter = painterResource(id = R.drawable.snakedash),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Image(
            painter = painterResource(id = R.drawable.sdlogofinal),
            contentDescription = "Logo",
            modifier = Modifier
                .size(350.dp)
                .align(Alignment.TopCenter)
                .padding(top = 53.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            gameSession?.let { session ->
                val players = session["players"] as List<String>? ?: listOf()

                if (players.size >= 2) {
                    Text(
                        "${players[0]} vs ${players[1]}",
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White, fontFamily = customFont)
                    )
                } else {
                    Text(
                        "Waiting for players...",
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White, fontFamily = customFont)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Start Game button
            Button(
                onClick = {
                    // Update Firestore to set the state as "Players Ready"
                    db.collection("game-session").document(gameId).update("state", "Players Ready")
                    navController.navigate("multiplayerGameScreen/$gameId/$username")
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(60.dp)
                    .background(Color(0xff242c11).copy(alpha = 0.8f), shape = RoundedCornerShape(20.dp))
                    .border(2.dp, Color.White, shape = RoundedCornerShape(20.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text(
                    text = "Start Game",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = customFont
                )
            }
        }
    }
}
