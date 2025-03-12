package com.snake.snakes2.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.snake.snakes2.viewmodel.GameViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.snake.snakes2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardsScreen(navController: NavController, username: String, viewModel: GameViewModel = viewModel()) {
    val scores = viewModel.allScores.collectAsState().value

    // Sorting the scores in descending order to show the highest scores at the top
    val sortedScores = scores.sortedByDescending { it.score }

    // Load custom fonts (for ranks and score)
    val inlandersFont = remember {
        try {
            FontFamily(Font(R.font.inlanders_font, FontWeight.Normal)) // Primary font for username and other text
        } catch (e: Exception) {
            FontFamily.Default // Fallback to default font if there's an error
        }
    }

    // Font for ranks (1st, 2nd, 3rd)
    val rankFont = remember {
        try {
            FontFamily(Font(R.font.score_font, FontWeight.Bold)) // Custom font for ranks
        } catch (e: Exception) {
            FontFamily.Default
        }
    }

    // Font for scores (to make the numbers more visible)
    val scoreFont = remember {
        try {
            FontFamily(Font(R.font.score_font, FontWeight.Bold)) // Custom font for score
        } catch (e: Exception) {
            FontFamily.Default
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Leaderboards") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("homeScreen/$username") }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Go Back")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("homeScreen/$username") }) {
                        Icon(Icons.Filled.Home, contentDescription = "Go Home")
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                // Background image like in HomeScreen
                Image(
                    painter = painterResource(id = R.drawable.snakedash), // Background image from your resources
                    contentDescription = "Leaderboard Background Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Column(modifier = Modifier.padding(paddingValues)) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(sortedScores) { score ->
                            val position = sortedScores.indexOf(score) + 1 // Ranking starts from 1

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .background(
                                        // Add background color based on position with transparency
                                        when (position) {
                                            1 -> Color(0xFFFFD700).copy(alpha = 0.8f) // Gold for 1st
                                            2 -> Color(0xFFC0C0C0).copy(alpha = 0.8f) // Silver for 2nd
                                            3 -> Color(0xFFCD7F32).copy(alpha = 0.8f) // Bronze for 3rd
                                            else -> Color(0xA0606060) // Slight transparent gray for 4th and below
                                        }
                                    )
                                    .clip(RoundedCornerShape(16.dp))
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Rank Label with specific font for ranks
                                Text(
                                    text = when (position) {
                                        1 -> "1st"
                                        2 -> "2nd"
                                        3 -> "3rd"
                                        else -> "${position}th"
                                    },
                                    modifier = Modifier.weight(0.2f),
                                    style = MaterialTheme.typography.bodyLarge.copy(fontFamily = rankFont) // Custom font for rank
                                )

                                // Username with bigger font size and icon if needed
                                Text(
                                    text = score.username,
                                    style = MaterialTheme.typography.bodyLarge.copy(fontFamily = inlandersFont, fontWeight = FontWeight.Bold),
                                    modifier = Modifier.weight(0.5f),
                                    color = Color.White
                                )

                                // Score with bigger size and different font for better visibility
                                Text(
                                    text = "${score.score}",
                                    style = MaterialTheme.typography.bodyLarge.copy(fontFamily = scoreFont), // Use font for score
                                    modifier = Modifier.weight(0.3f),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}
