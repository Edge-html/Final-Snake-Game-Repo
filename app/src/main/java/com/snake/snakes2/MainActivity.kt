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
                        composable("countScreen/{username}", arguments = listOf(navArgument("username") { type = NavType.StringType })) { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            CountScreen(navController = navController, username = username) // Now correctly passing username
                        }

                        composable("gameScreen/{username}", arguments = listOf(navArgument("username") { type = NavType.StringType })) { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            GameScreen(navController = navController, username = username)
                        }
                        composable("leaderboardsScreen/{username}", arguments = listOf(navArgument("username") { type = NavType.StringType })) { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            LeaderboardsScreen(navController = navController, username = username, viewModel = viewModel())
                        }


                    }
                }
            }
        }
    }
}