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

    var speed = 150L  // Initial speed in milliseconds

    suspend fun updateGame() {
        mutex.withLock {
            val newPosition = mutableState.value.snake.first().let { pos ->
                Pair(
                    (pos.first + move.dx + BOARD_SIZE) % BOARD_SIZE,
                    (pos.second + move.dy + BOARD_SIZE) % BOARD_SIZE
                )
            }

            val snakeLength = if (newPosition == mutableState.value.food) {
                // If snake eats food, increase length, score, and speed
                mutableState.update {
                    it.copy(
                        food = Pair(Random().nextInt(BOARD_SIZE), Random().nextInt(BOARD_SIZE)),
                        score = it.score + 10 // Increase score by 10 points
                    )
                }
                speed = (speed * 0.9).toLong().coerceAtLeast(50L)
                mutableState.value.snake.size + 1
            } else {
                mutableState.value.snake.size
            }

            mutableState.update {
                it.copy(snake = listOf(newPosition) + it.snake.take(snakeLength - 1))
            }
        }
    }

    companion object {
        const val BOARD_SIZE = 16
    }
}
