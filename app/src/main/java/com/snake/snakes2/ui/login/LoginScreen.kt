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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.snake.snakes2.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.VisualTransformation

import com.snake.snakes2.ui.theme.Snakes2Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, onLoginSuccess: () -> Unit, onSignUpClick: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    val inlandersFont = remember {
        try {
            FontFamily(Font(R.font.inlanders_font, FontWeight.Normal))
        } catch (e: Exception) {
            FontFamily.Default
        }
    }

    // 🔥 Login Form Screen (Landing screen has been removed)
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
                    .padding(top = 10.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 95.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .background(Color(0xff242c11).copy(alpha = 0.8f), shape = RoundedCornerShape(20.dp))
                        .border(2.dp, Color.White, shape = RoundedCornerShape(20.dp))
                        .padding(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .width(300.dp)
                            .wrapContentHeight() // 🔥 Fix: Allow the column to expand instead of cutting off content
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
                            modifier = Modifier.padding(bottom = 20.dp) // 🔥 Reduced padding to give more space
                        )

                        // Common modifier for uniform size
                        val textFieldSizeModifier = Modifier
                            .width(300.dp)
                            .height(55.dp)

                        // USERNAME FIELD
                        Text(
                            text = "USERNAME",
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .then(textFieldSizeModifier)
                                .background(Color(0xff5e6b38), shape = RoundedCornerShape(8.dp)) // Same as "PROCEED" button
                                .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp))
                        ) {
                            TextField(
                                value = username,
                                onValueChange = { username = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                placeholder = { Text("Enter a username", color = Color.White.copy(alpha = 0.6f)) },
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.Transparent,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(15.dp)) // 🔥 Added extra space

                        // PASSWORD FIELD
                        Text(
                            text = "PASSWORD",
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .then(textFieldSizeModifier)
                                .background(Color(0xff5e6b38), shape = RoundedCornerShape(8.dp)) // Same as "PROCEED" button
                                .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp))
                        ) {
                            var passwordVisible by remember { mutableStateOf(false) } // Correct state handling

                            TextField(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                placeholder = { Text(text = "Enter a password", color = Color.White.copy(alpha = 0.6f)) },
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.Transparent,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    val image = if (passwordVisible) R.drawable.visibilityon else R.drawable.visibilityoff

                                    IconButton(onClick = { passwordVisible = !passwordVisible }) { // ✅ Correct toggle logic
                                        Icon(
                                            painter = painterResource(id = image),
                                            contentDescription = "Toggle Password Visibility",
                                            tint = Color.White
                                        )
                                    }
                                }
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
                            if (username == "username" && password == "password") {
                                navController.navigate("gameScreen")
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
                        navController.navigate("signUpScreen") // 🔥 Navigate to SignUpScreen correctly
                    }
                )
            }
        }
    }
}
