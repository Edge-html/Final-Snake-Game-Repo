package com.snake.snakes2.data

data class GameState(
    val food: Pair<Int, Int>,
    val snake: List<Pair<Int, Int>>,
    val score: Int = 0 // Add score property
)
