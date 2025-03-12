//
package com.snake.snakes2.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.snake.snakes2.R
import com.snake.snakes2.viewmodel.GameViewModel

@Composable
fun MechanicsScreen(navController: NavController, viewModel: GameViewModel = viewModel(), username: String) {
    val inlandersFont = remember {
        try {
            FontFamily(Font(R.font.inlanders_font, FontWeight.Normal))
        } catch (e: Exception) {
            FontFamily.Default
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff242c11))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        // Game Logo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .border(4.dp, Color.White)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sdlogobg),
                contentDescription = "Game Logo Background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Game Mechanics Content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
                .border(4.dp, Color.White)
        ) {
            Image(
                painter = painterResource(id = R.drawable.gamescreenbg),
                contentDescription = "Game Mechanics Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "GAME MECHANICS",
                    fontSize = 34.sp,
                    color = Color.White,
                    fontFamily = inlandersFont,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )

                Spacer(modifier = Modifier.height(25.dp))

                val mechanicsList = listOf(
                    "Eating 1.00 grade gives +4 points",
                    "Eating 2.00 grade gives +3 points",
                    "Eating 3.00 grade gives +2 points",
                    "Eating IP gives +1 point"
                )

                mechanicsList.forEach {
                    Text(
                        text = it,
                        fontSize = 18.sp,
                        color = Color.White,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .width(220.dp)
                .height(55.dp)
                .background(Color(0xff5e6b38), shape = RoundedCornerShape(10.dp))
                .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp))
        ) {

            // NEXT Button
            Button(
                onClick = { navController.navigate("mechanics2Screen/$username") },
                modifier = Modifier
                    .width(220.dp)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xff5e6b38))
            ) {
                Text(
                    "NEXT",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = inlandersFont
                )
            }
        }
    }
}
