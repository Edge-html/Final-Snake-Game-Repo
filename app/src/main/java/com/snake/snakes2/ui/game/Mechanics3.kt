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
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.snake.snakes2.viewmodel.GameViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.snake.snakes2.R

@Composable
fun MechanicsScreen3(navController: NavController, username: String, viewModel: GameViewModel = viewModel()) {
    val inlandersFont = remember {
        try {
            FontFamily(Font(R.font.inlanders_font, FontWeight.Normal))
        } catch (e: Exception) {
            FontFamily.Default
        }
    }

    // Content for page 3
    val content = listOf(
        "ADDITIONAL REMINDERS",
        "1. As the game progresses, the snake's speed increases.",
        "2. If the snake moves into its own body (eats itself), it's game over.",
        "3. The snake will eat fruits in the order of IP, 1, 2, and 3, with each fruit giving different points."
    )

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

        // Game Mechanics Background with Content
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
                // Display the title and content text
                Text(
                    text = content[0],
                    fontSize = 34.sp,
                    color = Color.White,
                    fontFamily = inlandersFont,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(25.dp))

                // Loop through the content and display the items
                for (i in 1 until content.size) {
                    Text(
                        text = content[i],
                        fontSize = 18.sp,
                        color = Color.White,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(10.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Row for the BACK and PROCEED buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f) // Ensures equal width for buttons
                    .height(55.dp)
                    .background(Color(0xff5e6b38), shape = RoundedCornerShape(10.dp))
                    .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                Button(
                    onClick = { navController.navigate("mechanics2Screen/$username") },
                    modifier = Modifier.fillMaxSize(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Text(
                        "BACK",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = inlandersFont
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f) // Ensures equal width for buttons
                    .height(55.dp)
                    .background(Color(0xff5e6b38), shape = RoundedCornerShape(10.dp))
                    .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                Button(
                    onClick = { navController.navigate("CharSelScreen/$username") }, // Navigate to CharacterSelectionScreen
                    modifier = Modifier.fillMaxSize(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Text(
                        "PROCEED",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = inlandersFont
                    )
                }
            }
        }
    }
}