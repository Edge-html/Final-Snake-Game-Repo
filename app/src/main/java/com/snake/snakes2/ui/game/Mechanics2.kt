//
package com.snake.snakes2.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.GridCells
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
import com.snake.snakes2.viewmodel.GameViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.snake.snakes2.R

@Composable
fun MechanicsScreen2(navController: NavController, viewModel: GameViewModel = viewModel(), username: String) {
    val inlandersFont = remember {
        try {
            FontFamily(Font(R.font.inlanders_font, FontWeight.Normal))
        } catch (e: Exception) {
            FontFamily.Default
        }
    }

    // Define the sprite images list
    val spriteImages = listOf(
        R.drawable.sprite_anna,
        R.drawable.sprite_ums,
        R.drawable.sprite_gims,
        R.drawable.sprite_lyka,
        R.drawable.sprite_bea,
        R.drawable.sprite_ian
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

        // Game Mechanics Background with Sprite Images
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
                .border(4.dp, Color.White)
        ) {
            Image(
                painter = painterResource(id = R.drawable.gamescreenbg),
                contentDescription = "Game Mechanics Background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                // Spacer to move images further down
                Spacer(modifier = Modifier.height(40.dp)) // Adjust the space as needed

                // Display sprites in a grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(spriteImages) { imageResource ->
                        Box(
                            modifier = Modifier
                                .size(90.dp)  // Smaller image size
                                .padding(8.dp) // Remove background and border
                        ) {
                            Image(
                                painter = painterResource(id = imageResource),
                                contentDescription = "Sprite Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(23.dp))

                Text(
                    text = "The player can select a character (sprite) to represent the snake's head throughout the game.",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = inlandersFont,
                    modifier = Modifier
                        .fillMaxWidth() // Ensure the text takes up full width
                        .wrapContentWidth(Alignment.CenterHorizontally) // Center the text
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Row for the BACK and NEXT buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Added space between buttons
        ) {
            Box(
                modifier = Modifier
                    .weight(1f) // Ensures equal width for buttons
                    .height(55.dp)
                    .background(Color(0xff5e6b38), shape = RoundedCornerShape(10.dp))
                    .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                Button(
                    onClick = { navController.navigate("mechanicsScreen/$username") },
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
                    onClick = { navController.navigate("mechanics3Screen/$username") },
                    modifier = Modifier.fillMaxSize(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
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
}