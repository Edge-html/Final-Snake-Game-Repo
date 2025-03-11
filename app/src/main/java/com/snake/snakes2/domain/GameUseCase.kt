//GameUseCase.kt
package com.snake.snakes2.domain

import com.snake.snakes2.data.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.Random
import android.util.Log

class GameUseCase {
    private val mutex = Mutex()
    private val mutableState = MutableStateFlow(
        GameState(food = Pair(5, 5), snake = listOf(Pair(7, 7)), score = 0, gameOver = false)
    )
    val state = mutableState

    var move = Direction.RIGHT
    var speed = 150L
    private var fruitsEaten = 0
    private var gameOver = false

    suspend fun updateGame() {
        if (gameOver) return

        mutex.withLock {
            val newPosition = mutableState.value.snake.first().let { pos ->
                Pair(
                    (pos.first + move.dx + BOARD_SIZE) % BOARD_SIZE,
                    (pos.second + move.dy + BOARD_SIZE) % BOARD_SIZE
                )
            }

            if (mutableState.value.snake.contains(newPosition)) {
                gameOver = true
                mutableState.update { it.copy(gameOver = true) }
                Log.d("GameUseCase", "Game Over: Score = ${mutableState.value.score}")
                return
            }

            val isEatingFood = newPosition == mutableState.value.food
            val newSnake = listOf(newPosition) + mutableState.value.snake
            val updatedSnake = if (isEatingFood) {
                fruitsEaten++
                val pointsPerFruit = 10 + (fruitsEaten / 5) * 5
                mutableState.update {
                    it.copy(
                        food = Pair(Random().nextInt(BOARD_SIZE), Random().nextInt(BOARD_SIZE)),
                        score = it.score + pointsPerFruit
                    )
                }
                Log.d("GameUseCase", "Eating Food: New Score = ${mutableState.value.score}")
                speed = (speed * 0.9).toLong().coerceAtLeast(50L)
                newSnake
            } else {
                newSnake.dropLast(1)
            }

            mutableState.update { it.copy(snake = updatedSnake) }
        }
    }

    fun restartGame() {
        fruitsEaten = 0
        speed = 150L
        gameOver = false
        mutableState.update {
            it.copy(
                food = Pair(5, 5),
                snake = listOf(Pair(7, 7)),
                score = 0, // Reset score here
                gameOver = false
            )
        }
        Log.d("GameUseCase", "Game Restarted")
    }

    companion object {
        const val BOARD_SIZE = 16
    }
}
