package com.snake.snakes2.ui.game

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.snake.snakes2.viewmodel.GameViewModel

@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
    val state by gameViewModel.state.collectAsState()

    Log.d("GameScreen", "Rendering GameScreen with state: $state")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp), // Adjust top padding
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.snake.isEmpty()) {
            // Show "Game Over" if the snake is empty (game over state)
            Text(
                text = "Game Over!",
                fontSize = 48.sp,
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            // Enlarged Game Board (Increased the game area size)
            Box(
                modifier = Modifier
                    .size(500.dp) // Increase the game area size
                    .background(Color.Black) // Game area background color
            ) {
                Log.d("GameScreen", "Rendering Board with state: $state")
                Board(state) // Ensure Board is implemented correctly
            }

            // Score Display moved below the game board and above the controls
            Text(
                text = "Score: ${state.score}",
                fontSize = 28.sp,
                color = Color.Black, // Score color changed to black
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Game Controls (Direction buttons)
            Controls { direction ->
                gameViewModel.changeDirection(direction)
                Log.d("GameScreen", "Direction changed: $direction")
            }
        }
    }
}
