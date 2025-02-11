package com.snake.snakes2.ui.game

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.snake.snakes2.viewmodel.GameViewModel

@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
    val state = gameViewModel.state.collectAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Board(state.value)
        Controls { gameViewModel.changeDirection(it) }
    }
}
