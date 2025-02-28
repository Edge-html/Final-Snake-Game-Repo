//GameUseCase.kt
package com.snake.snakes2.domain

import com.snake.snakes2.data.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.Random

class GameUseCase {
    private val mutex = Mutex()
    private val mutableState = MutableStateFlow(
        GameState(food = Pair(5, 5), snake = listOf(Pair(7, 7)), score = 0)
    )
    val state = mutableState

    var move = Direction.RIGHT
        set(value) {
            field = value
        }

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

            // Check if the snake hits itself
            if (mutableState.value.snake.contains(newPosition)) {
                gameOver = true // Game over if snake touches its body
                mutableState.update {
                    it.copy(
                        snake = listOf(Pair(7, 7)), // Reset snake position
                        score = 0 // Reset score
                    )
                }
                return
            }

            val isEatingFood = newPosition == mutableState.value.food

            if (isEatingFood) {
                // Increase fruitsEaten count
                fruitsEaten++

                // Calculate score per fruit (increments every 5 fruits)
                val pointsPerFruit = 10 + (fruitsEaten / 5) * 5

                // Update state with new food position, increased score
                mutableState.update {
                    it.copy(
                        food = Pair(Random().nextInt(BOARD_SIZE), Random().nextInt(BOARD_SIZE)), // New food
                        score = it.score + pointsPerFruit
                    )
                }

                // Speed up the snake's movements
                speed = (speed * 0.9).toLong().coerceAtLeast(50L)
            }

            // Update snake's body
            val newSnake = listOf(newPosition) + mutableState.value.snake

            // If eating food, keep the full length
            val updatedSnake = if (isEatingFood) newSnake else newSnake.dropLast(1)

            mutableState.update {
                it.copy(snake = updatedSnake)
            }
        }
    }

    companion object {
        const val BOARD_SIZE = 16
    }
}
