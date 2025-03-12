//MultiplayerGameUseCase.kt
package com.snake.snakes2.domain

import android.util.Log
import com.snake.snakes2.data.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.Random
import com.google.firebase.firestore.FirebaseFirestore

class MultiplayerGameUseCase {
    private val mutex = Mutex()
    private val mutableState = MutableStateFlow(
        GameState(
            food = mapOf("x" to Random().nextInt(BOARD_SIZE), "y" to Random().nextInt(BOARD_SIZE)),
            snake1 = listOf(mapOf("x" to 7, "y" to 7)),
            snake2 = listOf(mapOf("x" to 9, "y" to 9)),
            score1 = 0,
            score2 = 0,
            gameOver = false,
            hostUsername = null,
            isHost = false,
            players = listOf(),
            state = "waiting for players"
        )
    )
    val state = mutableState

    suspend fun moveSnake(snakeNumber: Int, direction: Direction, gameId: String) {
        mutex.withLock {
            val newState = mutableState.value
            val snake = if (snakeNumber == 1) newState.snake1 else newState.snake2

            if (snake.isEmpty()) {
                Log.d("MultiplayerGameUseCase", "Attempted to move an empty snake.")
                return
            }

            val newHead = snake.first().let { pos ->
                mapOf(
                    "x" to (pos["x"]!! + direction.dx + BOARD_SIZE) % BOARD_SIZE,
                    "y" to (pos["y"]!! + direction.dy + BOARD_SIZE) % BOARD_SIZE
                )
            }

            if (newState.snake1.contains(newHead) || newState.snake2.contains(newHead)) {
                mutableState.update { it.copy(gameOver = true) }
                FirebaseFirestore.getInstance().collection("game-session")
                    .document(gameId)
                    .update("gameOver", true, "score1", newState.score1, "score2", newState.score2)
                return
            }

            val newSnake = listOf(newHead) + snake.dropLast(1)

            if (snakeNumber == 1) {
                mutableState.update { it.copy(snake1 = newSnake) }
                FirebaseFirestore.getInstance().collection("game-session")
                    .document(gameId)
                    .update("snake1", newSnake)
            } else {
                mutableState.update { it.copy(snake2 = newSnake) }
                FirebaseFirestore.getInstance().collection("game-session")
                    .document(gameId)
                    .update("snake2", newSnake)
            }
        }
    }

    // Start Timer When Players Are Ready
    fun startTimerIfReady() {
        if (mutableState.value.state == "Players Ready") {
            // Start the game timer here
            // Your timer logic should go here
        }
    }

    fun restartGame() {
        mutableState.update {
            it.copy(
                snake1 = listOf(mapOf("x" to 7, "y" to 7)),
                snake2 = listOf(mapOf("x" to 9, "y" to 9)),
                score1 = 0,
                score2 = 0,
                gameOver = false,
                state = "waiting for players"
            )
        }
    }

    fun updateGameState(gameState: GameState) {
        mutableState.update { gameState }
    }

    companion object {
        const val BOARD_SIZE = 16
    }
}
