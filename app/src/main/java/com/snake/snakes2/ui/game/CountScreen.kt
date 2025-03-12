//CountScreen.kt
package com.snake.snakes2.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.snake.snakes2.R
import com.snake.snakes2.viewmodel.GameViewModel
import kotlinx.coroutines.delay

@Composable
fun CountScreen(navController: NavController, username: String, selectedCharacter: Int, viewModel: GameViewModel = viewModel()) {
    var countdown by remember { mutableStateOf(3) }

    val inlandersFont = remember {
        try {
            FontFamily(Font(R.font.inlanders_font, FontWeight.Normal))
        } catch (e: Exception) {
            FontFamily.Default
        }
    }

    // Countdown logic
    LaunchedEffect(Unit) {
        while (countdown > 0) {
            delay(1000L)  // Wait for 1 second before decreasing the countdown
            countdown -= 1
        }
        // After countdown ends, navigate to the game screen
        navController.navigate("gameScreen/$username/$selectedCharacter") { // Pass both username and selected character here
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xff242c11))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Ready Player: $username", color = Color.White, fontSize = 24.sp, fontFamily = inlandersFont)
            Spacer(modifier = Modifier.height(20.dp))
            Text("$countdown", color = Color.White, fontSize = 48.sp)
        }
    }
}

