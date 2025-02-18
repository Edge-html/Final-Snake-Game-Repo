package com.snake.snakes2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snake.snakes2.domain.GameUseCase
import com.snake.snakes2.domain.Direction
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private val gameUseCase = GameUseCase()
    val state = gameUseCase.state

    init {
        viewModelScope.launch {
            while (true) {
                delay(gameUseCase.speed)
                gameUseCase.updateGame()
            }
        }
    }

    fun changeDirection(direction: Direction) {
        gameUseCase.move = direction
    }
}
