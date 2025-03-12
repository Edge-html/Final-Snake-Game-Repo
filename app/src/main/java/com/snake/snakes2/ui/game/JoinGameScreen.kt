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
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.snake.snakes2.R

@Composable
fun JoinGameScreen(navController: NavController, username: String) {
    val db = FirebaseFirestore.getInstance()
    var gameSessions by remember { mutableStateOf(listOf<Pair<String, Map<String, Any>>>()) }

    LaunchedEffect(key1 = true) {
        db.collection("game-session")
            .whereEqualTo("state", "waiting for players")
            .get()
            .addOnSuccessListener { documents ->
                gameSessions = documents.map { document ->
                    Pair(document.id, document.data)
                }
            }
    }

    val customFont = FontFamily(Font(R.font.score_font, FontWeight.Bold))

    Box(modifier = Modifier.fillMaxSize().background(Color(0xff242c11))) {
        Image(
            painter = painterResource(id = R.drawable.snakedash),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Available Game Sessions",
                style = MaterialTheme.typography.titleLarge.copy(color = Color.White, fontFamily = customFont)
            )
            Spacer(modifier = Modifier.height(24.dp))

            gameSessions.forEach { (gameId, session) ->
                val hostUsername = session["hostUsername"] as? String ?: "Unknown Host"
                HomeButton("Join Game ID: $gameId hosted by $hostUsername") {
                    val gameRef = db.collection("game-session").document(gameId)
                    db.runTransaction { transaction ->
                        val snapshot = transaction.get(gameRef)
                        val currentPlayers = snapshot.get("players") as? MutableList<String> ?: mutableListOf()
                        if (!currentPlayers.contains(username)) {
                            currentPlayers.add(username)
                            transaction.update(gameRef, "players", currentPlayers)
                            transaction.update(gameRef, "state", "Players Ready")
                        }
                    }.addOnSuccessListener {
                        navController.navigate("gameRoom/$gameId/$username")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HomeButton("Back") {
                navController.navigateUp()
            }
        }
    }
}
