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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.snake.snakes2.R
import com.snake.snakes2.viewmodel.GameViewModel

@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
    val state by gameViewModel.state.collectAsState()

    Log.d("GameScreen", "Rendering GameScreen with state: $state")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff242c11))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔹 Top Image (sdlogobg.jpg) with white border
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
        ) {
            val imageMaxWidth = maxWidth - 16.dp
            Box(
                modifier = Modifier
                    .width(imageMaxWidth)
                    .height(150.dp)
                    .border(4.dp, Color.White) // White border around the image
                    .padding(10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sdlogobg),
                    contentDescription = "Game Logo Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize() // Ensure the image fills the box
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp)) // Space between sections

        // 🔹 Game Board placed over `gamescreenbg.jpg`
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Log.d("GameScreen", "Rendering Board with state: $state")
            Board(state) // Game board is now placed over `gamescreenbg.jpg`
        }

        Spacer(modifier = Modifier.height(16.dp)) // Space before the score section

        // 🔹 Score Display below the game board
        Text(
            text = "Score: ${state.score}",
            fontSize = 28.sp,
            color = Color.White, // Adjusted color for better visibility
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(20.dp)) // Space before the controls

        // 🔹 Game Controls (Direction buttons)
        Controls { direction ->
            gameViewModel.changeDirection(direction)
            Log.d("GameScreen", "Direction changed: $direction")
        }
    }
}

