//Board.kt
package com.snake.snakes2.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.snake.snakes2.R
import com.snake.snakes2.data.GameState

@Composable
fun Board(state: GameState) {
    BoxWithConstraints(Modifier.padding(16.dp)) {
        val tileSize = maxWidth / 16 // Adjust BOARD_SIZE if needed

        val imageMaxWidth = maxWidth - 16.dp
        val imageMaxHeight = maxHeight - 16.dp

        val imageHeight = 350.dp // Set height for the background image

        // Draw the game background image (gamescreenbg) with a white border
        Box(
            modifier = Modifier
                .fillMaxWidth()  // Ensure the width fills the available space
                .height(imageHeight) // Set the height for the image
                .border(4.dp, Color.White)  // Add a white border around the image
                .padding(10.dp)  // Add padding around the image
        ) {
            Image(
                painter = painterResource(id = R.drawable.gamescreenbg),
                contentDescription = "Game background",
                contentScale = ContentScale.Crop, // Ensure the image crops to fit
                modifier = Modifier.fillMaxSize() // Fill the Box with the background image
            )
        }

        // Draw Food
        Box(
            Modifier
                .offset(
                    x = (tileSize * state.food.first).coerceAtMost(imageMaxWidth),
                    y = (tileSize * state.food.second).coerceAtMost(imageMaxHeight)
                )
                .size(tileSize)
                .background(Color.Red, CircleShape)
        )

        // Draw Snake
        state.snake.forEach {
            Box(
                modifier = Modifier
                    .offset(
                        x = (tileSize * it.first).coerceAtMost(imageMaxWidth),
                        y = (tileSize * it.second).coerceAtMost(imageMaxHeight)
                    )
                    .size(tileSize)
                    .background(Color.Green, CircleShape)
            )
        }
    }
}
