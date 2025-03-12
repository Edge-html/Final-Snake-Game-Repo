//GameRoomScreen.kt
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

    Box(modifier = Modifier.fillMaxSize().background(Color(0xff242c11))) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Game Room for Game ID: $gameId", color = Color.White, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Host: $username", color = Color.White, fontSize = 24.sp)
            Text("Players in this room:", color = Color.White, fontSize = 24.sp)
            players.forEach { player ->
                Text(player, style = MaterialTheme.typography.bodyMedium.copy(color = Color.White))
            }

            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { navController.navigate("multiplayerArena/$gameId/$username") }) {
                Text("Start Game")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigateUp() }) {
                Text("Leave Room")
            }
        }
    }
}
