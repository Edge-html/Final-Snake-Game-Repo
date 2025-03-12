package com.snake.snakes2.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snake.snakes2.domain.GameUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.snake.snakes2.data.GameState
import com.snake.snakes2.domain.Direction
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ScoreEntry(val username: String, val score: Int)

class GameViewModel : ViewModel() {
    private val gameUseCase = GameUseCase() // Correctly referencing GameUseCase
    val state = gameUseCase.state
    private val _highestScore = MutableStateFlow<Pair<String, Int>?>(null)
    val highestScore = _highestScore.asStateFlow()
    private val _allScores = MutableStateFlow<List<ScoreEntry>>(emptyList())
    val allScores: StateFlow<List<ScoreEntry>> = _allScores

    private val _lastScore = MutableStateFlow(0)
    val lastScore: StateFlow<Int> = _lastScore

    private val _navigationEvent = Channel<NavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            while (true) {
                delay(gameUseCase.speed)
                gameUseCase.updateGame()
                if (state.value.gameOver) {
                    setLastScore(state.value.score)
                }
            }
        }
        fetchAllScores()
        fetchHighestScore()
    }

    private fun fetchAllScores() {
        FirebaseFirestore.getInstance().collection("scores")
            .orderBy("score", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val highestScores = mutableMapOf<String, ScoreEntry>()
                result.forEach { document ->
                    val username = document.getString("username") ?: "Unknown"
                    val score = document.getLong("score")?.toInt() ?: 0
                    highestScores[username] = highestScores[username]?.let {
                        if (score > it.score) ScoreEntry(username, score) else it
                    } ?: ScoreEntry(username, score)
                }
                _allScores.value = highestScores.values.toList()
            }
            .addOnFailureListener { e ->
                Log.e("GameViewModel", "Error loading scores", e)
            }
    }

    private fun fetchHighestScore() {
        FirebaseFirestore.getInstance().collection("scores")
            .orderBy("score", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.documents.isNotEmpty()) {
                    val highest = documents.documents.first()
                    val username = highest.getString("username") ?: "Unknown"
                    val scoreString = highest.get("score")?.toString() ?: "0"
                    try {
                        val score = if (scoreString.isBlank()) 0 else scoreString.toInt()
                        _highestScore.value = Pair(username, score)
                    } catch (e: NumberFormatException) {
                        _highestScore.value = Pair(username, 0)
                    }
                } else {
                    _highestScore.value = Pair("Unknown", 0)
                }
            }
            .addOnFailureListener {
                _highestScore.value = Pair("Unknown", 0)
            }
    }

    fun setLastScore(score: Int) {
        _lastScore.value = score
    }

    fun getLastScore(): Int = _lastScore.value

    fun restartGame() {
        gameUseCase.restartGame()
        _lastScore.value = 0
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

    // Implement the updateGameState method
    fun updateGameState(gameState: GameState) {
        // Update the state of the game with the provided gameState.
        state.update { gameState }
    }
}
