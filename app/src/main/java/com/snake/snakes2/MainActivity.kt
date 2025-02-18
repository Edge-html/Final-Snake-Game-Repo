//MainActivity.kt
package com.snake.snakes2

import GameScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.snake.snakes2.ui.login.LoginScreen
import com.snake.snakes2.ui.theme.Snakes2Theme
import com.snake.snakes2.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            Snakes2Theme {
                Surface {
                    var isLoggedIn by remember { mutableStateOf(false)}

                    if (isLoggedIn) {
                        GameScreen(viewModel<GameViewModel>())
                    } else {
                        LoginScreen(onLoginSuccess = { isLoggedIn = true })
                    }

                }
            }
        }
    }
}
