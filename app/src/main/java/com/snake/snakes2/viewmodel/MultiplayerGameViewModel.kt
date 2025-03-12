//MultiplayerGameViewModel
package com.snake.snakes2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snake.snakes2.domain.MultiplayerGameUseCase
import kotlinx.coroutines.launch
import com.snake.snakes2.domain.Direction
import kotlinx.coroutines.delay
import androidx.compose.runtime.mutableStateOf
import com.snake.snakes2.data.GameState


class MultiplayerGameViewModel(private val gameUseCase: MultiplayerGameUseCase) : ViewModel() {

    val state = mutableStateOf(GameState())
    var timer = 60
    var gameOver = false
        private set

    init {
        startGameTimer()
    }

    fun startGameTimer() {
        viewModelScope.launch {
            while (timer > 0 && !gameOver) {
                kotlinx.coroutines.delay(1000)
                timer--
                gameUseCase.updateGameState(state.value.copy(timer = timer))
            }
            if (timer == 0) {
                gameOver = true
                gameUseCase.updateGameState(state.value.copy(gameOver = true))
            }
        }
    }

    fun moveSnake(snakeNumber: Int, direction: Direction, gameId: String) {
        viewModelScope.launch {
            gameUseCase.moveSnake(snakeNumber, direction, gameId)
        }
    }

    fun restartGame(gameId: String) {
        gameOver = false
        timer = 60
        gameUseCase.restartGame()
        startGameTimer()
    }
}
data class GameState(
    val score1: Int = 0,
    val score2: Int = 0,
    val players: List<String> = listOf(),
)
