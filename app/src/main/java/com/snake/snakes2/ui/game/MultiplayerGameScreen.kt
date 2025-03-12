package com.snake.snakes2.ui.game

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.snake.snakes2.viewmodel.MultiplayerGameViewModel
import com.snake.snakes2.viewmodel.MultiplayerGameViewModelFactory
import com.snake.snakes2.domain.MultiplayerGameUseCase


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun MultiplayerGameScreen(
    navController: NavController,
    gameId: String,
    username: String
) {
    val viewModel: MultiplayerGameViewModel = viewModel(
        factory = MultiplayerGameViewModelFactory(gameUseCase = MultiplayerGameUseCase())
    )

    val state = viewModel.state.value
    val timer = viewModel.timer
    val gameOver = viewModel.gameOver
    val score1 = state.score1
    val score2 = state.score2

    // Ensure that the players list is not empty before proceeding
    if (state.players.isEmpty()) {
        // Handle empty players list, show loading or wait for Firestore update
        return
    }

    // Update game state if players are ready
    LaunchedEffect(key1 = state.state) {
        if (state.state == "Players Ready") {
            // Start the timer and game when players are ready
            viewModel.startGameTimer()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Display the game board
        MultiplayerBoard(state, username)

        Spacer(modifier = Modifier.height(16.dp))

        // Display timer
        Text(text = "Time Remaining: $timer")

        // Controls for player movements
        Controls { direction ->
            if (!gameOver) {
                val snakeNumber = if (state.players[0] == username) 1 else 2
                viewModel.moveSnake(snakeNumber, direction, gameId)
            }
        }

        // When the game is over, show the result and options
        if (gameOver) {
            val winner = if (score1 > score2) "Player 1" else "Player 2"
            val loser = if (score1 < score2) "Player 1" else "Player 2"

            GameOverPopup(winner, loser) { rematch ->
                if (rematch) {
                    viewModel.restartGame(gameId)
                } else {
                    navController.navigate("homeScreen/$username")
                }
            }
        }
    }
}



@Composable
fun GameOverPopup(winner: String, loser: String, onRematch: (Boolean) -> Unit) {
    AlertDialog(
        onDismissRequest = { onRematch(false) },
        title = { Text("Game Over") },
        text = { Text("$winner wins! $loser loses.") },
        confirmButton = {
            Button(onClick = { onRematch(true) }) {
                Text("Rematch")
            }
        },
        dismissButton = {
            Button(onClick = { onRematch(false) }) {
                Text("Quit")
            }
        }
    )
}
