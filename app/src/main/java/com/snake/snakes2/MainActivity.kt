package com.snake.snakes2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.snake.snakes2.ui.login.LoginScreen
import com.snake.snakes2.ui.theme.Snakes2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            Snakes2Theme {
                Surface {
                    var isLoggedIn by remember { mutableStateOf(true) }

                  //  Log.d("MainActivity", "Current isLoggedIn state: $isLoggedIn")

                    var isLoginScreen by remember { mutableStateOf(true) }

                    if (isLoginScreen) {
                        LoginScreen(
                            onLoginSuccess = { isLoggedIn = true },
                            onSignUpClick = { isLoginScreen = false } // Navigate to SignUpScreen
                        )
                    } else {
                        SignUpScreen(
                            onSignUpSuccess = { isLoginScreen = true }, // Navigate back after signup
                            onBackToLogin = { isLoginScreen = true } // Handle back navigation
                        )
                    }
                    }
                }
            }
        }
    }

