//MainActivity.kt
package com.snake.snakes2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.snake.snakes2.ui.login.LoginScreen
import com.snake.snakes2.ui.game.GameScreen
import com.snake.snakes2.ui.theme.Snakes2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            Snakes2Theme {
                var isGameScreen by remember { mutableStateOf(false) }
                var isLoginScreen by remember { mutableStateOf(true) }

                Surface {
                    if (isGameScreen) {
                        // Show the Game Screen after successful login
                        GameScreen()
                    } else {
                        // Show the login screen
                        LoginScreen(
                            onLoginSuccess = {
                                isLoginScreen = false
                                isGameScreen = true // Navigate to the game screen after login
                            },
                            onSignUpClick = { isLoginScreen = false } // Navigate to SignUpScreen
                        )
                    }
                }
            }
        }
    }
}
