package com.snake.snakes2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.snake.snakes2.domain.MultiplayerGameUseCase

class MultiplayerGameViewModelFactory(private val gameUseCase: MultiplayerGameUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MultiplayerGameViewModel::class.java)) {
            return MultiplayerGameViewModel(gameUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
