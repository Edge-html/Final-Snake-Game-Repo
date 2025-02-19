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
            .background(Color(0xFF1E1E1E)), // Dark background for visibility
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Score Display
        Text(
            text = "Score: ${state.score}",
            fontSize = 28.sp,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )

        // Game Board
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.Black) // Background to clearly show the board
        ) {
            Log.d("GameScreen", "Rendering Board with state: $state")
            Board(state) // Ensure Board is implemented correctly
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Game Controls
        Controls { direction ->
            gameViewModel.changeDirection(direction)
            Log.d("GameScreen", "Direction changed: $direction")
        }
    }
}
