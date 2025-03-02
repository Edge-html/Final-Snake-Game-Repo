package com.snake.snakes2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.snake.snakes2.ui.login.LoginScreen
import com.snake.snakes2.ui.signup.SignUpScreen
import com.snake.snakes2.ui.game.GameScreen
import com.snake.snakes2.ui.game.HomeScreen
import com.snake.snakes2.ui.home.LandingScreen
import com.snake.snakes2.ui.theme.Snakes2Theme

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
                                onLoginSuccess = {
                                    // Navigate to HomeScreen after successful login
                                    navController.navigate("homeScreen") {
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
                        composable("homeScreen") {
                            HomeScreen(navController = navController) // Navigate to the home screen after login
                        }

                        // 🔹 Game Screen
                        composable("gameScreen") {
                            GameScreen() // Navigate to GameScreen when required
                        }
                    }
                }
            }
        }
    }
}
