//Leaderboards.kt
package com.snake.snakes2.ui.game

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.snake.snakes2.viewmodel.GameViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardsScreen(navController: NavController, username: String, viewModel: GameViewModel = viewModel()) {
    val scores = viewModel.allScores.collectAsState().value

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
        //
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(scores) { score ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = score.username, style = MaterialTheme.typography.bodyLarge)
                            Text(text = "${score.score}", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
        }
    )
}
