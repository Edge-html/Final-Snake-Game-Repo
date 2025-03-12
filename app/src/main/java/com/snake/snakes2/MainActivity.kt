//MainActivity.kt
package com.snake.snakes2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.snake.snakes2.ui.login.LoginScreen
import com.snake.snakes2.ui.signup.SignUpScreen
import com.snake.snakes2.ui.game.GameScreen
import com.snake.snakes2.ui.game.HomeScreen
import com.snake.snakes2.ui.game.CountScreen // Import CountScreen
import com.snake.snakes2.ui.home.LandingScreen
import com.snake.snakes2.ui.theme.Snakes2Theme
import com.snake.snakes2.ui.game.LeaderboardsScreen
import com.snake.snakes2.ui.game.MultiplayerScreen
import com.snake.snakes2.ui.game.GameRoomScreen
import com.snake.snakes2.ui.game.JoinGameScreen
import com.snake.snakes2.ui.game.MultiplayerArena
import com.snake.snakes2.ui.game.MultiplayerGameScreen
import com.snake.snakes2.ui.game.MechanicsScreen
import com.snake.snakes2.ui.game.MechanicsScreen2
import com.snake.snakes2.ui.game.MechanicsScreen3
import com.snake.snakes2.ui.game.CharacterSelectionScreen
import com.snake.snakes2.viewmodel.MultiplayerGameViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            Snakes2Theme {
                val navController = rememberNavController() // Initialize navigation controller

                Surface {
                    // Define the NavHost with the HomeScreen after login
                    NavHost(navController = navController, startDestination = "landingScreen") {
                        // 🔹 Landing Screen
                        composable("landingScreen") {
                            LandingScreen(navController = navController) // Navigate to LoginScreen when clicked
                        }

                        // 🔹 Login Screen
                        composable("loginScreen") {
                            LoginScreen(
                                navController = navController, // Pass navController
                                onLoginSuccess = { username ->
                                    // Navigate to HomeScreen with the username after successful login
                                    navController.navigate("homeScreen/$username") {
                                        // This ensures that after navigating to HomeScreen, the user can't go back to the LoginScreen
                                        popUpTo("loginScreen") { inclusive = true }
                                    }
                                },
                                onSignUpClick = { navController.navigate("signUpScreen") } // Navigate to SignUpScreen
                            )
                        }

                        // 🔹 Sign-Up Screen
                        composable("signUpScreen") {
                            SignUpScreen(
                                navController = navController, // Pass navController
                                onSignUpSuccess = {
                                    navController.navigate("loginScreen") // Navigate to LoginScreen after successful sign-up
                                },
                                onBackToLogin = {
                                    navController.navigate("loginScreen") // Navigate to LoginScreen when clicking "Already have an account? Log in"
                                }
                            )
                        }

                        // 🔹 Home Screen (This will be displayed after a successful login)
                        composable("homeScreen/{username}", arguments = listOf(navArgument("username") { type = NavType.StringType })) { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            HomeScreen(navController, username)
                        }


                        // 🔹 Count Screen with username parameter
                        composable("countScreen/{username}/{selectedCharacter}", arguments = listOf(
                            navArgument("username") { type = NavType.StringType },
                            navArgument("selectedCharacter") { type = NavType.StringType }
                        )) { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            val selectedCharacter = backStackEntry.arguments?.getString("selectedCharacter") ?: ""
                            CountScreen(navController = navController, username = username, selectedCharacter = selectedCharacter)
                        }


                        composable("gameScreen/{username}", arguments = listOf(navArgument("username") { type = NavType.StringType })) { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            GameScreen(navController = navController, username = username)
                        }
                        composable("leaderboardsScreen/{username}", arguments = listOf(navArgument("username") { type = NavType.StringType })) { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            LeaderboardsScreen(navController = navController, username = username, viewModel = viewModel())
                        }
                        composable("multiplayerScreen/{username}", arguments = listOf(navArgument("username") { type = NavType.StringType })) { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            MultiplayerScreen(navController = navController, username = username)
                        }

                        //MULTIPLAYER
                        composable("gameRoom/{gameId}/{username}", arguments = listOf(
                            navArgument("gameId") { type = NavType.StringType },
                            navArgument("username") { type = NavType.StringType }
                        )) { backStackEntry ->
                            val gameId = backStackEntry.arguments?.getString("gameId") ?: ""
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            GameRoomScreen(navController, gameId, username)  // Implement this Composable to handle the game room
                        }

                        composable("joinGameScreen/{username}", arguments = listOf(
                            navArgument("username") { type = NavType.StringType }
                        )) { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            JoinGameScreen(navController, username)  // Implement this Composable to handle joining games
                        }

                        composable("multiplayerGameScreen/{gameId}/{username}", arguments = listOf(
                            navArgument("gameId") { type = NavType.StringType },
                            navArgument("username") { type = NavType.StringType }
                        )) { backStackEntry ->
                            val gameId = backStackEntry.arguments?.getString("gameId") ?: ""
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            MultiplayerGameScreen(navController = navController, gameId = gameId, username = username)
                        }






                        composable(
                            route = "multiplayerArena/{gameId}/{username}",
                            arguments = listOf(
                                navArgument("gameId") { type = NavType.StringType },
                                navArgument("username") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val gameId = backStackEntry.arguments?.getString("gameId") ?: ""
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            MultiplayerArena(navController, gameId, username)  // Now passing username along with gameId
                        }

                        // 🔹 Mechanics Screen
                        composable("mechanics3Screen/{username}", arguments = listOf(navArgument("username") { type = NavType.StringType })) { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            MechanicsScreen3(navController = navController, username = username, viewModel = viewModel())
                        }

                        composable("mechanicsScreen/{username}", arguments = listOf(navArgument("username") { type = NavType.StringType })) { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            MechanicsScreen(navController = navController, username = username, viewModel = viewModel())
                        }

                        composable("mechanics2Screen/{username}", arguments = listOf(navArgument("username") { type = NavType.StringType })) { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            MechanicsScreen2(navController = navController, username = username, viewModel = viewModel())
                        }
                        composable("CharSelScreen/{username}", arguments = listOf(navArgument("username") { type = NavType.StringType })) { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            CharacterSelectionScreen(navController = navController, username = username, viewModel = viewModel())
                        }



                    }
                }
            }
        }
    }
}