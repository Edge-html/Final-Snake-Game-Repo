package com.snake.snakes2.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.snake.snakes2.R

@Composable
fun LandingScreen(navController: NavController) { // ✅ Accepts NavController to navigate to LoginScreen

    val inlandersFont = remember {
        try {
            FontFamily(Font(R.font.inlanders_font, FontWeight.Normal))
        } catch (e: Exception) {
            FontFamily.Default
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                navController.navigate("loginScreen") // ✅ Navigates to Login Screen when clicked
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.snakedash),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Image(
            painter = painterResource(id = R.drawable.sdlogofinal),
            contentDescription = "Logo",
            modifier = Modifier
                .size(350.dp)
                .align(Alignment.TopCenter)
                .padding(top = 53.dp)
        )

        Text(
            text = "Click anywhere to start",
            color = Color.White,
            fontSize = 24.sp,
            fontFamily = inlandersFont, // ✅ Corrected: Use the variable directly
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(40.dp)
        )
    }
}
