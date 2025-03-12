//MultiplayerBoard.kt
package com.snake.snakes2.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.snake.snakes2.data.GameState
import com.snake.snakes2.domain.MultiplayerGameUseCase

@Composable
fun MultiplayerBoard(state: GameState, username: String) {
    // Check if players are available and not empty
    if (state.players.isEmpty()) {
        // Handle the case when players are not yet populated (e.g., show a loading indicator)
        return
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val tileSize = minOf(maxWidth, maxHeight) / MultiplayerGameUseCase.BOARD_SIZE.toFloat()

        // Draw food
        Box(
            modifier = Modifier
                .offset(x = tileSize * state.food["x"]!!, y = tileSize * state.food["y"]!!)
                .size(tileSize)
                .background(Color.Red, CircleShape)
        )

        // Draw Snake based on current player
        val snakeToDraw = if (state.players[0] == username) state.snake1 else state.snake2
        snakeToDraw.forEach { segment ->
            Box(
                modifier = Modifier
                    .offset(x = tileSize * segment["x"]!!, y = tileSize * segment["y"]!!)
                    .size(tileSize)
                    .background(if (state.players[0] == username) Color.Green else Color.Blue, CircleShape)
            )
        }
    }
}
