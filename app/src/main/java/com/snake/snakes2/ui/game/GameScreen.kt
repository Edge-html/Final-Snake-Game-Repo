//GameScreen.kt
package com.snake.snakes2.ui.game

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.snake.snakes2.R
import com.snake.snakes2.viewmodel.GameViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel(), navController: NavController, username: String) {
    val state by gameViewModel.state.collectAsState()
    val inlandersFont = remember {
        try {
            FontFamily(Font(R.font.inlanders_font, FontWeight.Normal))
        } catch (e: Exception) {
            FontFamily.Default
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff242c11))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.gameOver) {
            GameOverContent(
                score = state.score,
                username = username, // Pass username here
                onRestart = {
                    gameViewModel.restartGame()
                },
                onGoHome = {
                    navController.navigate("homeScreen") {
                        popUpTo("gameScreen") { inclusive = true }
                    }
                }
            )
        } else {
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val imageMaxWidth = maxWidth - 16.dp
                Box(
                    modifier = Modifier
                        .width(imageMaxWidth)
                        .height(100.dp)
                        .border(4.dp, Color.White)
                        .padding(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sdlogobg),
                        contentDescription = "Game Logo Background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .border(10.dp, Color.White)
            ) {
                Board(state)
            }

            // Display the live score during gameplay
            Text(
                text = "Score: ${state.score}",
                fontSize = 28.sp,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Player: $username!",
                color = Color.White,
                fontFamily = inlandersFont,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            //
            Controls { direction ->
                gameViewModel.changeDirection(direction)
            }
        }
    }
}

@Composable
fun GameOverContent(score: Int, username: String, onRestart: () -> Unit, onGoHome: () -> Unit) {
    val db = FirebaseFirestore.getInstance()

    // We'll use this to ensure the update only runs once per game over
    var hasUpdatedScore by remember { mutableStateOf(false) }

    // This will run once when GameOverContent is first composed and the game is over
    LaunchedEffect(key1 = score) {
        if (!hasUpdatedScore) {
            db.collection("scores")
                .add(mapOf("username" to username, "score" to score))
                .addOnSuccessListener {
                    Log.d("Firestore", "New score successfully added for user: $username")
                    hasUpdatedScore = true
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error adding new score for user: $username", e)
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Game Over", fontSize = 32.sp, color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onRestart()
                hasUpdatedScore = false  // Reset the flag so score can be updated next game over
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Restart")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                onGoHome()
                hasUpdatedScore = false  // Reset the flag so score can be updated next game over
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Home")
        }
    }
}