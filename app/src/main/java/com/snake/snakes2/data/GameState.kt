//GameState.kt
package com.snake.snakes2.data

data class GameState(
    val food: Map<String, Int> = mapOf("x" to 0, "y" to 0),  // Use Map for coordinates
    val snake: List<Map<String, Int>> = listOf(),  // List of coordinates as Map
    val score: Int = 0,

    val snake1: List<Map<String, Int>> = listOf(mapOf("x" to 7, "y" to 7)),  // Initial position for Player 1
    val snake2: List<Map<String, Int>> = listOf(mapOf("x" to 9, "y" to 9)),  // Initial position for Player 2
    val score1: Int = 0,
    val score2: Int = 0,
    val gameOver: Boolean = false,
    val hostUsername: String? = null,
    val isHost: Boolean = false,
    val players: List<String> = listOf(),
    val state: String = "",

    // New fields for storing directions
    val direction1: String = "UP",  // Player 1's direction
    val direction2: String = "DOWN",
    val playerReady1: Boolean = false,
    val playerReady2: Boolean = false,
    val timer: Int = 60 // Player 2's direction
)
