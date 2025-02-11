package com.snake.snakes2.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.snake.snakes2.data.GameState

@Composable
fun Board(state: GameState) {
    BoxWithConstraints(Modifier.padding(16.dp)) {
        val tileSize = maxWidth / 16 // Adjust BOARD_SIZE if needed

        Box(
            Modifier
                .size(maxWidth)
                .border(2.dp, Color.DarkGray)
        )

        // Draw Food
        Box(
            Modifier
                .offset(x = tileSize * state.food.first, y = tileSize * state.food.second)
                .size(tileSize)
                .background(Color.Red, CircleShape)
        )

        // Draw Snake
        state.snake.forEach {
            Box(
                modifier = Modifier
                    .offset(x = tileSize * it.first, y = tileSize * it.second)
                    .size(tileSize)
                    .background(Color.Green, CircleShape)
            )
        }
    }
}
