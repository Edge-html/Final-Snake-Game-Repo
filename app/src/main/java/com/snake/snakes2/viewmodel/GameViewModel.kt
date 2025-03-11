//GameViewModel.kt
package com.snake.snakes2.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.snake.snakes2.domain.GameUseCase
import com.snake.snakes2.domain.Direction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.asStateFlow
data class ScoreEntry(val username: String, val score: Int)
class GameViewModel : ViewModel() {
    private val _highestScore = MutableStateFlow<Pair<String, Int>?>(null)
    val highestScore = _highestScore.asStateFlow()
    private val gameUseCase = GameUseCase()
    val state = gameUseCase.state
    private val _allScores = MutableStateFlow<List<ScoreEntry>>(emptyList())
    val allScores: StateFlow<List<ScoreEntry>> = _allScores

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
    init {
        fetchAllScores()
    }
    private fun fetchAllScores() {
        Firebase.firestore.collection("scores")
            .orderBy("score", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val scores = result.map { doc ->
                    ScoreEntry(
                        username = doc.getString("username") ?: "Unknown",
                        score = doc.getLong("score")?.toInt() ?: 0
                    )
                }
                _allScores.value = scores
            }
            .addOnFailureListener { e ->
                Log.e("GameViewModel", "Error loading scores", e)
            }
    }

    init {
        fetchHighestScore()
    }

    private fun fetchHighestScore() {
        FirebaseFirestore.getInstance().collection("scores")
            .orderBy("score", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                Log.d("GameViewModel", "Firestore query successful. Number of documents: ${documents.documents.size}")
                if (documents.documents.isNotEmpty()) {
                    val highest = documents.documents.first()
                    val username = highest.getString("username") ?: "Unknown"
                    val scoreString = highest.get("score")?.toString() ?: "0"

                    Log.d("GameViewModel", "Retrieved document: username=$username, score=$scoreString")

                    try {
                        val score = if (scoreString.isBlank()) 0 else scoreString.toInt()
                        _highestScore.value = Pair(username, score)
                        Log.d("GameViewModel", "Highest score set: $username with score $score")
                    } catch (e: NumberFormatException) {
                        Log.e("GameViewModel", "Error parsing score: $scoreString", e)
                        _highestScore.value = Pair(username, 0)
                    }
                } else {
                    Log.d("GameViewModel", "No scores found in Firestore")
                    _highestScore.value = Pair("Unknown", 0)
                }
            }
            .addOnFailureListener {
                Log.e("GameViewModel", "Error fetching highest score", it)
                _highestScore.value = Pair("Unknown", 0)
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