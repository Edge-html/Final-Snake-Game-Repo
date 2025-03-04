package com.snake.snakes2.ui.game

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.snake.snakes2.R

@Composable
fun HomeScreen(navController: NavController, username: String) {
    val inlandersFont = remember {
        try {
            FontFamily(Font(R.font.inlanders_font, FontWeight.Normal))
        } catch (e: Exception) {
            FontFamily.Default
        }
    }

    // Home Screen Layout
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.snakedash),
            contentDescription = "Home Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Image(
            painter = painterResource(id = R.drawable.sdlogofinal),
            contentDescription = "Game Logo",
            modifier = Modifier
                .size(350.dp)
                .align(Alignment.TopCenter)
                .padding(top = 40.dp) // Adjust to your preferred logo position
        )

        // Welcome message
        Text(
            text = "Welcome, $username!",
            color = Color.White,
            fontFamily = inlandersFont,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 30.dp) // Space between the welcome message and buttons
        )

        // Buttons layout
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp), // Adjust padding to place buttons properly
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Single Player Button
            HomeButton(
                text = "Single Player",
                onClick = {
                    Log.d("HomeScreen", "Single Player button clicked")
                    // Navigate to CountScreen (for countdown)
                    navController.navigate("countScreen")
                }
            )

            // Multiplayer Button
            HomeButton(
                text = "Multiplayer",
                onClick = {
                    navController.navigate("multiplayerScreen") // Navigate to Multiplayer screen
                }
            )

            // Leaderboards Button
            HomeButton(
                text = "Leaderboards",
                onClick = {
                    navController.navigate("leaderboardsScreen") // Navigate to Leaderboards screen
                }
            )

            // Settings Button
            HomeButton(
                text = "Settings",
                onClick = {
                    navController.navigate("settingsScreen") // Navigate to Settings screen
                }
            )

            // Credits Button
            HomeButton(
                text = "Credits",
                onClick = {
                    navController.navigate("creditsScreen") // Navigate to Credits screen
                }
            )

            // Logout Button
            HomeButton(
                text = "Logout",
                onClick = {
                    navController.navigate("loginScreen") // Navigate to Login screen
                }
            )
        }
    }
}

@Composable
fun HomeButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(Color(0xff242c11).copy(alpha = 0.8f), shape = RoundedCornerShape(20.dp))
            .border(2.dp, Color.White, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth(0.8f) // Adjust width to your design
            .height(60.dp)
            .clickable { onClick() }
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.inlanders_font, FontWeight.Normal)),
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
