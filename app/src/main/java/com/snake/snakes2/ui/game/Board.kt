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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.snake.snakes2.data.GameState
import com.snake.snakes2.R

@Composable
fun Board(state: GameState, imageHeight: Dp) {
    BoxWithConstraints(Modifier.padding(16.dp)) {
        // Calculate the tile size based on the available space (width or height)
        val tileSize = (minOf(maxWidth, maxHeight) / 15) // 15x15 grid

        val imageMaxWidth = maxWidth - 15.dp
        val imageMaxHeight = maxHeight - 16.dp

        // Draw Food (Red Ball)
        Box(
            Modifier
                .offset(
                    x = (tileSize * state.food.first).coerceAtMost(imageMaxWidth),
                    y = (tileSize * state.food.second).coerceAtMost(imageMaxHeight)
                )
                .size(tileSize)
                .background(Color.Red, CircleShape)
        )

        // Draw Snake's Head (Selected Character Sprite)
        val headPosition = state.snake.first()
        Box(
            modifier = Modifier
                .offset(
                    x = (tileSize * headPosition.first).coerceAtMost(imageMaxWidth),
                    y = (tileSize * headPosition.second).coerceAtMost(imageMaxHeight)
                )
                .size(tileSize)
                .background(Color.Transparent, CircleShape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sprite_anna), // Replace with dynamic sprite
                contentDescription = "Snake Head",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Draw Snake's Body (Brown Balls)
        state.snake.drop(1).forEach {
            Box(
                modifier = Modifier
                    .offset(
                        x = (tileSize * it.first).coerceAtMost(imageMaxWidth),
                        y = (tileSize * it.second).coerceAtMost(imageMaxHeight)
                    )
                    .size(tileSize)
                    .background(Color(0xFF6B4F3C), CircleShape)  // Brown color for body

            )
        }
    }
}
