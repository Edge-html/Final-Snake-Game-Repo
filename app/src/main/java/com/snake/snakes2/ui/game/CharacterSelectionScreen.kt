package com.snake.snakes2.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
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
import androidx.navigation.NavHostController
import com.snake.snakes2.R
import com.snake.snakes2.viewmodel.GameViewModel

@Composable
fun CharacterSelectionScreen(navController: NavHostController, viewModel: GameViewModel = viewModel(), username: String) {
    var selectedCharacter by remember { mutableStateOf<Int?>(null) }  // Store selected character
    var selectedCharacterName by remember { mutableStateOf("") }  // Store selected character's name
    var showMessage by remember { mutableStateOf(false) } // Show message if no character is selected
    val inlandersFont = remember {
        try {
            FontFamily(Font(R.font.inlanders_font, FontWeight.Normal))
        } catch (e: Exception) {
            FontFamily.Default
        }
    }

    // Define the sprite images list
    val spriteImages = listOf(
        Pair(R.drawable.sprite_anna, "Ermeter"),
        Pair(R.drawable.sprite_ums, "Jeo"),
        Pair(R.drawable.sprite_gims, "Ken"),
        Pair(R.drawable.sprite_lyka, "Pia"),
        Pair(R.drawable.sprite_bea, "BiniBeybee"),
        Pair(R.drawable.sprite_ian, "Ian Mae")
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
                Spacer(modifier = Modifier.height(20.dp)) // Adjust the space as needed

                Text(
                    text = "SELECT YOUR CHARACTER!",
                    fontSize = 25.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = inlandersFont,
                    modifier = Modifier
                        .fillMaxWidth() // Ensure the text takes up full width
                        .wrapContentWidth(Alignment.CenterHorizontally) // Center the text
                )

                Spacer(modifier = Modifier.height(20.dp)) // Adjust the space as needed

                // Display sprites in a grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(spriteImages) { (imageResource, name) ->
                        val borderColor = if (selectedCharacter == imageResource) Color.Green else Color.White
                        Box(
                            modifier = Modifier
                                .size(110.dp)  // Increase the box size to allow space for the border
                                .border(4.dp, borderColor, shape = RoundedCornerShape(8.dp))  // Green border when selected
                                .clickable {
                                    selectedCharacter = imageResource // Set selected sprite image
                                    selectedCharacterName = name // Store the selected character name
                                    showMessage = false  // Reset message when a character is selected
                                }
                                .padding(8.dp), // Padding between the border and the image
                            contentAlignment = Alignment.Center
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

                // Show a message if no character is selected
                if (showMessage) {
                    Text(
                        text = "Please select a character to proceed.",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                // Display the selected character's name
                if (selectedCharacterName.isNotEmpty()) {
                    Text(
                        text = "Character $selectedCharacterName is selected.",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        fontFamily = inlandersFont,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
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
                    onClick = { navController.navigate("mechanicsScreen") },
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
                    onClick = {
                        // Proceed only if a character is selected
                        if (selectedCharacter != null) {
                            navController.navigate("countScreen/{username}/$selectedCharacter")
                        } else {
                            showMessage = true // Show message if no character is selected
                        }
                    },
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
