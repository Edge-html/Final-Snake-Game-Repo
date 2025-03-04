package com.snake.snakes2.ui.game

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun CountScreen(navController: NavController) {
    var countdown by remember { mutableStateOf(3) }

    // Countdown logic
    LaunchedEffect(Unit) {
        while (countdown > 0) {
            delay(1000L)  // Wait for 1 second before decreasing the countdown
            countdown -= 1
        }
        // After countdown ends, navigate to the game screen
        navController.navigate("gameScreen") {
            // Clear the back stack so the user can't go back to this screen
            popUpTo("countScreen") { inclusive = true }
        }
    }

    // Countdown screen layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF242C11)) // Background color
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$countdown",
            color = Color.White,
            fontSize = 64.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
