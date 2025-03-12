//GameScreen.kt
package com.snake.snakes2.ui.game

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.google.firebase.firestore.FirebaseFirestore
import com.snake.snakes2.domain.Direction

@Composable
fun GameScreen(
    gameViewModel: GameViewModel = viewModel(),
    navController: NavController,
    username: String,
    selectedCharacter: Int
) {
    val state by gameViewModel.state.collectAsState()
    val highestScore by gameViewModel.highestScore.collectAsState()
    val inlandersFont = remember {
        try {
            FontFamily(Font(R.font.inlanders_font, FontWeight.Normal))
        } catch (e: Exception) {
            FontFamily.Default
        }
    }

    val sprite = painterResource(id = selectedCharacter)

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
                highestScore = highestScore ?: Pair("None", 0),
                username = username,
                onRestart = {
                    gameViewModel.restartGame()
                },
                onGoHome = {
                    navController.navigate("homeScreen/$username") {
                        popUpTo("gameScreen") { inclusive = true }
                    }
                }
            )
        } else {
            // Move down the logo with extra Spacer
            Spacer(modifier = Modifier.height(30.dp))

            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val imageMaxWidth = maxWidth - 16.dp
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp) // Increase the height to move it further down
                        .border(4.dp, Color.White)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sdlogobg),
                        contentDescription = "Game Logo Background",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Add extra space below the logo
            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp) // Increase height to move everything below
                    .border(4.dp, Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gamescreenbg),
                    contentDescription = "Game Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )


                // Add the selected sprite as the snake's head (the first ball before the snake eats the fruit)
                selectedCharacter?.let { spriteId ->
                    Box(
                        modifier = Modifier
                            .size(50.dp) // Snake's head size
                            .background(Color.Transparent)
                            .border(2.dp, Color.White, shape = RoundedCornerShape(50)) // Circular border for the head
                            .align(Alignment.TopStart) // Align the snake's head to the start of the board
                    ) {
                        Image(
                            painter = painterResource(id = spriteId),
                            contentDescription = "Snake Head",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Board(
                    state = state,
                    imageHeight = 380.dp
                )
            }

            // Increase space below game area
            Spacer(modifier = Modifier.height(10.dp))

            // Display the live score and player name side by side
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween // Aligns elements to opposite sides
            ) {
                Text(
                    text = "Score: ${state.score}",
                    fontSize = 28.sp,
                    color = Color.White,
                )

                Text(
                    text = "Player: $username",
                    color = Color.White,
                    fontFamily = inlandersFont,
                    fontSize = 24.sp,
                )
            }

            Box(
                modifier = Modifier
                    .offset(y = (-20).dp) // Move controls upwards without affecting others
            ) {
                Controls { direction: Direction ->
                    gameViewModel.changeDirection(direction)
                }
            }
        }
    }
}


//
@Composable
fun GameOverContent(score: Int, highestScore: Pair<String, Int>, username: String, onRestart: () -> Unit, onGoHome: () -> Unit) {
    val db = FirebaseFirestore.getInstance()

    val inlandersFont = remember {
        try {
            FontFamily(Font(R.font.inlanders_font, FontWeight.Normal))
        } catch (e: Exception) {
            FontFamily.Default
        }
    }

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
            .background(Color(0xFF242C11))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Game Over!", fontSize = 60.sp, color = Color(0Xffa51e1e), fontFamily = inlandersFont)
        Spacer(modifier = Modifier.height(0.dp))
        Text(text = "HIGHEST SCORE: ${highestScore.second} by ${highestScore.first}", fontSize = 24.sp, color = Color(0xff94a548), fontWeight = FontWeight.Bold)
        Text(text = "SCORE: $score", fontSize = 30.sp, color = Color.White, fontWeight = FontWeight.Bold)


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onRestart()
                hasUpdatedScore = false  // Reset the flag so score can be updated next game over
            },
            modifier = Modifier
                .fillMaxWidth(0.6f) // Match the width to look like "PROCEED"
                .height(50.dp) // Match the height proportionally
                .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp)), // Proper border
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff94a548)), // Set the background color
            shape = RoundedCornerShape(8.dp) // Ensures rounded edges
        ) {
            Text(
                text = "Restart",
                color = Color.White, // Text color for readability
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp)) // Adjust spacing

        Button(
            onClick = {
                onGoHome()
                hasUpdatedScore = false  // Reset the flag so score can be updated next game over
            },
            modifier = Modifier
                .fillMaxWidth(0.6f) // Same width as Restart button
                .height(50.dp) // Consistent height
                .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp)), // Ensure border is on the outside
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff94a548)), // Match the background color
            shape = RoundedCornerShape(8.dp) // Keep button shape
        ) {
            Text(
                text = "Go to Home",
                color = Color.White, // Ensure visibility
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}
