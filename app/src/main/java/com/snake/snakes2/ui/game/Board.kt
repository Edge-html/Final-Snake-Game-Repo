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
        // Calculate the tile size based on the available space (width or height)
        val tileSize = (minOf(maxWidth, maxHeight) / 15) // 15x15 grid

        val imageMaxWidth = maxWidth - 15.dp
        val imageMaxHeight = maxHeight - 16.dp

        // Dynamically adjust image size based on the maxWidth or maxHeight
        val imageHeight = minOf(maxWidth, maxHeight) // Use the smallest dimension

        // Draw the game background image with a white border
        Box(
            modifier = Modifier
                .width(imageHeight)  // Ensure the width fits the image
                .height(imageHeight) // Use imageHeight to keep the aspect ratio square
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

