package com.snake.snakes2.ui.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snake.snakes2.R
import com.snake.snakes2.ui.theme.Snakes2Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onSignUpClick: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isHomeScreen by remember { mutableStateOf(false) }
    var isStartingScreen by remember { mutableStateOf(true) } // Add state for the starting screen
    val context = LocalContext.current

    val inlandersFont = remember {
        try {
            FontFamily(Font(R.font.inlanders_font, FontWeight.Normal))
        } catch (e: Exception) {
            FontFamily.Default
        }
    }

    if (isStartingScreen) {
        // Starting Screen with "Click anywhere to start"
        Snakes2Theme {
            Box(modifier = Modifier.fillMaxSize().clickable {
                isStartingScreen = false // Hide starting screen and show login
            }) {
                Image(
                    painter = painterResource(id = R.drawable.snakedash),
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Image(
                    painter = painterResource(id = R.drawable.sdlogofinal),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(350.dp)
                        .align(Alignment.TopCenter)
                        .padding(top = 53.dp)
                )
                Text(
                    text = "Click anywhere to start",
                    color = Color.White,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
        }
    } else if (!isHomeScreen) {
        // Login Form Screen
        Snakes2Theme {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.snakedash),
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Image(
                    painter = painterResource(id = R.drawable.sdlogofinal),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(350.dp)
                        .align(Alignment.TopCenter)
                        .padding(top = 53.dp)
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xff242c11).copy(alpha = 0.8f), shape = RoundedCornerShape(20.dp))
                            .border(2.dp, Color.White, shape = RoundedCornerShape(20.dp))
                            .padding(20.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .width(350.dp)
                                .height(300.dp)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "LOGIN",
                                color = Color.White,
                                fontFamily = inlandersFont,
                                fontSize = 32.sp,
                                letterSpacing = 4.sp,
                                modifier = Modifier.padding(bottom = 30.dp)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Pre-fill dummy account values (username: user, password: password)
                            username = "user"
                            password = "password"

                            // USERNAME FIELD
                            Text("USERNAME", color = Color.White, fontSize = 18.sp)
                            Box(
                                modifier = Modifier
                                    .width(300.dp)
                                    .height(55.dp)
                                    .background(Color(0xff5e6b38), shape = RoundedCornerShape(10.dp))
                                    .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp))
                            ) {
                                TextField(
                                    value = username,
                                    onValueChange = { username = it },
                                    modifier = Modifier.fillMaxSize(),
                                    singleLine = true,
                                    colors = TextFieldDefaults.textFieldColors(
                                        containerColor = Color.Transparent,
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // PASSWORD FIELD
                            Text("PASSWORD", color = Color.White, fontSize = 18.sp)
                            Box(
                                modifier = Modifier
                                    .width(300.dp)
                                    .height(55.dp)
                                    .background(Color(0xff5e6b38), shape = RoundedCornerShape(10.dp))
                                    .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp))
                            ) {
                                TextField(
                                    value = password,
                                    onValueChange = { password = it },
                                    modifier = Modifier.fillMaxSize(),
                                    singleLine = true,
                                    colors = TextFieldDefaults.textFieldColors(
                                        containerColor = Color.Transparent,
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(
                        modifier = Modifier
                            .width(220.dp)
                            .height(55.dp)
                            .background(Color(0xff5e6b38), shape = RoundedCornerShape(10.dp))
                            .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp))
                    ) {
                        Button(
                            onClick = {
                                // Check dummy credentials (username: user, password: password)
                                if (username == "user" && password == "password") {
                                    isHomeScreen = true // Show home menu
                                } else {
                                    Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.fillMaxSize(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Text("PROCEED", color = Color.White, fontSize = 18.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "Don't have an account? Sign up",
                        color = Color.White,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable {
                            onSignUpClick() // Navigate to Sign Up Screen
                        }
                    )
                }
            }
        }
    } else {
        // Home Menu Screen
        Snakes2Theme {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.snakedash),
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Image(
                    painter = painterResource(id = R.drawable.sdlogofinal),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(350.dp)
                        .align(Alignment.TopCenter)
                        .padding(top = 53.dp)
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Start Game Button
                    Box(
                        modifier = Modifier
                            .width(250.dp)
                            .height(55.dp)
                            .background(Color(0xff5e6b38), shape = RoundedCornerShape(10.dp))
                            .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp))
                    ) {
                        Button(
                            onClick = { onLoginSuccess() }, // Proceed to the game screen
                            modifier = Modifier.fillMaxSize(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Text("START GAME", color = Color.White, fontSize = 18.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Resume Game Button
                    Box(
                        modifier = Modifier
                            .width(250.dp)
                            .height(55.dp)
                            .background(Color(0xff5e6b38), shape = RoundedCornerShape(10.dp))
                            .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp))
                    ) {
                        Button(
                            onClick = {
                                Toast.makeText(context, "Resuming Game", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.fillMaxSize(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Text("RESUME GAME", color = Color.White, fontSize = 18.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Leaderboards Button
                    Box(
                        modifier = Modifier
                            .width(250.dp)
                            .height(55.dp)
                            .background(Color(0xff5e6b38), shape = RoundedCornerShape(10.dp))
                            .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp))
                    ) {
                        Button(
                            onClick = {
                                Toast.makeText(context, "Resuming Game", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.fillMaxSize(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Text("LEADERBOARDS", color = Color.White, fontSize = 18.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Settings Button
                    Box(
                        modifier = Modifier
                            .width(250.dp)
                            .height(55.dp)
                            .background(Color(0xff5e6b38), shape = RoundedCornerShape(10.dp))
                            .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp))
                    ) {
                        Button(
                            onClick = {
                                Toast.makeText(context, "Opening Settings", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.fillMaxSize(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Text("SETTINGS", color = Color.White, fontSize = 18.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Logout Button
                    Box(
                        modifier = Modifier
                            .width(250.dp)
                            .height(55.dp)
                            .background(Color(0xff5e6b38), shape = RoundedCornerShape(10.dp))
                            .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp))
                    ) {
                        Button(
                            onClick = {
                                Toast.makeText(context, "Logging out", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.fillMaxSize(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Text("LOGOUT", color = Color.White, fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }
}
