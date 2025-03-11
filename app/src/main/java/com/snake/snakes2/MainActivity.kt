// MainActivity.kt
package com.snake.snakes2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.google.firebase.auth.FirebaseAuth
import com.snake.snakes2.ui.login.LoginScreen
import com.snake.snakes2.ui.signup.SignUpScreen
import com.snake.snakes2.ui.game.GameScreen
import com.snake.snakes2.ui.game.HomeScreen
import com.snake.snakes2.ui.game.CountScreen
import com.snake.snakes2.ui.home.LandingScreen
import com.snake.snakes2.ui.theme.Snakes2Theme

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth // Firebase Authentication

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setContent {
            Snakes2Theme {
                val navController = rememberNavController()

                val currentUser = auth.currentUser
                val startDestination = if (currentUser != null) {
                    "homeScreen/${currentUser.email}"
                } else {
                    "landingScreen"
                }

                Surface {
                    NavHost(navController = navController, startDestination = startDestination) {

                        // Landing Screen
                        composable("landingScreen") {
                            LandingScreen(navController = navController)
                        }

                        // Login Screen
                        composable("loginScreen") {
                            LoginScreen(
                                navController = navController,
                                onLoginSuccess = { username ->
                                    navController.navigate("homeScreen/$username") {
                                        popUpTo("landingScreen") { inclusive = true }
                                    }
                                },
                                onSignUpClick = { navController.navigate("signUpScreen") }
                            )
                        }

                        // Sign-Up Screen
                        composable("signUpScreen") {
                            SignUpScreen(
                                navController = navController,
                                onSignUpSuccess = {
                                    navController.navigate("loginScreen")
                                },
                                onBackToLogin = {
                                    navController.navigate("loginScreen")
                                }
                            )
                        }

                        // Home Screen
                        composable(
                            "homeScreen/{username}",
                            arguments = listOf(navArgument("username") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val username =
                                backStackEntry.arguments?.getString("username") ?: "Guest"
                            HomeScreen(navController = navController, username = username)
                        }

                        // Count Screen
                        composable(
                            "countScreen/{username}",
                            arguments = listOf(navArgument("username") { type = NavType.StringType })
                        ) { backStackEntry ->
                            CountScreen(
                                navController = navController,
                                username = backStackEntry.arguments?.getString("username") ?: "Guest"
                            )
                        }

                        // Game Screen
                        composable(
                            "gameScreen/{username}",
                            arguments = listOf(navArgument("username") { type = NavType.StringType })
                        ) { backStackEntry ->
                            GameScreen(
                                navController = navController,
                                username = backStackEntry.arguments?.getString("username") ?: "Guest"
                            )
                        }
                    }
                }
            }
        }
    }
}