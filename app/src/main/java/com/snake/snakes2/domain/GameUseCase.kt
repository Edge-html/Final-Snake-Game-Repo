//GameUseCase/kt
package com.snake.snakes2.domain

import android.util.Log
import com.snake.snakes2.data.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.Random

class GameUseCase {
    private val mutex = Mutex()
    private val mutableState = MutableStateFlow(
        GameState(food = mapOf("x" to 5, "y" to 5), snake = listOf(mapOf("x" to 7, "y" to 7)), score = 0, gameOver = false)
    )
    val state = mutableState

    var move = Direction.RIGHT
    var speed = 150L
    private var fruitsEaten = 0
    private var gameOver = false

    suspend fun updateGame() {
        mutex.withLock {
            val state = mutableState.value

            // Check if snake list is empty before processing
            if (state.snake.isEmpty()) {
                Log.w("GameUseCase", "Snake list is empty, skipping update.")
                return
            }

            val newPosition = state.snake.first().let { pos ->
                mapOf(
                    "x" to (pos["x"]!! + move.dx + BOARD_SIZE) % BOARD_SIZE,
                    "y" to (pos["y"]!! + move.dy + BOARD_SIZE) % BOARD_SIZE
                )
            }

            if (state.snake.contains(newPosition)) {
                gameOver = true
                mutableState.update { it.copy(gameOver = true) }
                Log.d("GameUseCase", "Game Over: Score = ${state.score}")
                return
            }

            val isEatingFood = newPosition == state.food
            val newSnake = listOf(newPosition) + state.snake
            val updatedSnake = if (isEatingFood) {
                fruitsEaten++
                val pointsPerFruit = 10 + (fruitsEaten / 5) * 5
                mutableState.update {
                    it.copy(
                        food = mapOf("x" to Random().nextInt(BOARD_SIZE), "y" to Random().nextInt(BOARD_SIZE)),
                        score = it.score + pointsPerFruit
                    )
                }
                Log.d("GameUseCase", "Eating Food: New Score = ${state.score}, Fruits Eaten: $fruitsEaten")
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
                food = mapOf("x" to 5, "y" to 5),
                snake = listOf(mapOf("x" to 7, "y" to 7)),
                score = 0,
                gameOver = false
            )
        }
        Log.d("GameUseCase", "Game Restarted")
    }

    companion object {
        const val BOARD_SIZE = 16
    }
}