//HomeScreen.kt
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.snake.snakes2.R

@Composable
fun HomeScreen(navController: NavController, username: String) {
    val auth = FirebaseAuth.getInstance() // Firebase Authentication instance

    val inlandersFont = remember {
        try {
            FontFamily(Font(R.font.inlanders_font, FontWeight.Normal))
        } catch (e: Exception) {
            FontFamily.Default
        }
    }

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
                .padding(top = 40.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Welcome message
            Text(
                text = "Welcome, $username!",
                color = Color.White,
                fontFamily = inlandersFont,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // Buttons
            HomeButton("Single Player") {
                Log.d("HomeScreen", "Single Player button clicked")
                navController.navigate("countScreen/$username") // Ensure username is included in the route
            }
            HomeButton("Multiplayer") {
                navController.navigate("multiplayerScreen")
            }

            HomeButton("Leaderboards") {
                Log.d("Leaderboards", "Leaderboards button clicked")
                navController.navigate("leaderboardsScreen/$username")
            }

            //
            HomeButton("Settings") {
                navController.navigate("settingsScreen")
            }

            HomeButton("Credits") {
                navController.navigate("creditsScreen")
            }

            HomeButton("Logout") {
                auth.signOut() // Logout from Firebase
                navController.navigate("loginScreen") {
                    popUpTo("homeScreen") { inclusive = true }
                }
            }
        }
    }
}

@Composable
fun HomeButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(Color(0xff242c11).copy(alpha = 0.8f), shape = RoundedCornerShape(20.dp))
            .border(2.dp, Color.White, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth(0.8f)
            .height(60.dp)
            .clickable { onClick() }
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
