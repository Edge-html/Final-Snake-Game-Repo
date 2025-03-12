//Board.kt
package com.snake.snakes2.ui.game


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import android.util.Log
import kotlin.math.min
import com.snake.snakes2.R
import com.snake.snakes2.data.GameState

@Composable
fun Board(state: GameState) {
    // Track the number of food placements (not fruits eaten)
    var foodPlacementCount by remember { mutableIntStateOf(0) }

    // Color cycling based on food placements
    val foodColors = listOf(Color.Red, Color(0xFFFFA500), Color.Yellow, Color(0x9ACD32), Color.Green) // Red, Orange, Yellow, YellowGreen, Green
    val currentFoodColor = when {
        foodPlacementCount < 5 -> foodColors[0]
        foodPlacementCount < 10 -> foodColors[1]
        foodPlacementCount < 15 -> foodColors[2]
        foodPlacementCount < 20 -> foodColors[3]
        else -> foodColors[4]
    }

    //Log the color
    val colorName = when (currentFoodColor) {
        Color.Red -> "Red"
        Color(0xFFFFA500) -> "Orange"
        Color.Yellow -> "Yellow"
        Color(0x9ACD32) -> "YellowGreen"
        Color.Green -> "Green"
        else -> "Unknown"
    }

    BoxWithConstraints(Modifier.padding(16.dp)) {
        // Calculate the tile size based on the available space (width or height)
        val tileSize = (minOf(maxWidth, maxHeight) / 16) // 16x16 grid

        val imageMaxWidth = maxWidth - 15.dp
        val imageMaxHeight = maxHeight - 16.dp

        // Dynamically adjust image size based on the maxWidth or maxHeight
        val imageHeight = minOf(maxWidth, maxHeight) // Use the smallest dimension

        // Draw the game background image with a white border
        Box(
            modifier = Modifier
                .width(imageHeight)  // Ensure the width fits the image
                .height(imageHeight) // Use imageHeight to keep the aspect ratio square
                .padding(10.dp)  // Add padding around the image
        ) {
            Image(
                painter = painterResource(id = R.drawable.gamescreenbg),
                contentDescription = "Game background",
                contentScale = ContentScale.Crop, // Ensure the image crops to fit
                modifier = Modifier.fillMaxSize() // Fill the Box with the background image
            )
        }

        // Draw Food (changing color based on placement)
        Box(
            Modifier
                .offset(
                    x = (tileSize * state.food["x"]!!).coerceAtMost(imageMaxWidth), // Accessing x from Map
                    y = (tileSize * state.food["y"]!!).coerceAtMost(imageMaxHeight)  // Accessing y from Map
                )
                .size(tileSize)
                .background(currentFoodColor, CircleShape) // Use the dynamic color based on food placements
        )

        // Draw Snake's Head (Selected Character Sprite)
        val headPosition = state.snake.first() // Assuming state.snake is a list of maps (e.g., Map<String, Int>)
        Box(
            modifier = Modifier
                .offset(
                    x = (tileSize * headPosition["x"]!!).coerceAtMost(imageMaxWidth),
                    y = (tileSize * headPosition["y"]!!).coerceAtMost(imageMaxHeight)
                )
                .size(tileSize)
                .background(Color.Transparent, CircleShape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sprite_anna), // Replace with dynamic sprite
                contentDescription = "Snake Head",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Draw Snake's Body (Green Circles for Body)
        state.snake.drop(1).forEach { bodyPart ->
            Box(
                modifier = Modifier
                    .offset(
                        x = (tileSize * bodyPart["x"]!!).coerceAtMost(imageMaxWidth),
                        y = (tileSize * bodyPart["y"]!!).coerceAtMost(imageMaxHeight)
                    )
                    .size(tileSize)
                    .background(Color.Gray, CircleShape)  // Gray color for body
            )
        }

        // Update foodPlacementCount whenever a fruit is eaten
        LaunchedEffect(state.food) {
            if (state.snake.first() == state.food) {
                foodPlacementCount += 1 // Increase food placements count
                Log.d("Board", "Food Eaten! foodPlacementCount: $foodPlacementCount, Color: $colorName") //Log the count and color
            }
        }
    }
}