//GameViewModel.kt
package com.snake.snakes2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snake.snakes2.domain.GameUseCase
import com.snake.snakes2.domain.Direction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private val gameUseCase = GameUseCase()
    val state = gameUseCase.state

    private val _lastScore = MutableStateFlow(0)
    val lastScore: StateFlow<Int> = _lastScore

    // SharedFlow to emit navigation events
    private val _navigationEvent = Channel<NavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            while (true) {
                delay(gameUseCase.speed)
                gameUseCase.updateGame()

                // Store the last score before game over
                if (state.value.gameOver) {
                    setLastScore(state.value.score)
                }
            }
        }
    }

    fun setLastScore(score: Int) {
        _lastScore.value = score // Stores the last score correctly
    }

    fun getLastScore(): Int {
        return _lastScore.value
    }

    fun restartGame() {
        gameUseCase.restartGame()
        _lastScore.value = 0  // Reset last score
    }

    fun changeDirection(direction: Direction) {
        gameUseCase.move = direction
    }

    fun goToHome() {
        viewModelScope.launch {
            _navigationEvent.send(NavigationEvent.GoToHome)
        }
    }

    sealed class NavigationEvent {
        object GoToHome : NavigationEvent()
    }
}