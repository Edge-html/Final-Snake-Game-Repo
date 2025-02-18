package com.snake.snakes2.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snake.snakes2.R
import com.snake.snakes2.ui.theme.Snakes2Theme

class LoginScreen(onLoginSuccess: () -> Unit) : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Snakes2Theme {
                val context = LocalContext.current // Required for Toast

                var username by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                val inlandersFont = remember {
                    try {
                        FontFamily(Font(R.font.inlanders_font, FontWeight.Normal))
                    } catch (e: Exception) {
                        FontFamily.Default
                    }
                }

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.snakedash),
                        contentDescription = "Background Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize() // Correctly placed
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
                        // LOGIN BOX
                        Box(
                            modifier = Modifier
                                .background(Color(0xff242c11).copy(alpha = 0.8f), shape = RoundedCornerShape(20.dp))
                                .border(
                                    width = 2.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(20.dp)
                                )
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
                                // LOGIN TITLE
                                Text(
                                    text = "LOGIN",
                                    color = Color.White,
                                    fontFamily = inlandersFont,
                                    fontSize = 32.sp,
                                    letterSpacing = 4.sp,
                                    modifier = Modifier.padding(bottom = 30.dp)
                                )

                                Spacer(modifier = Modifier.height(10.dp))

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

                        // BUTTON BELOW LOGIN BOX
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
                                    Toast.makeText(context, "Username: $username, Password: $password", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.fillMaxSize(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                            ) {
                                Text("PROCEED", color = Color.White, fontSize = 18.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
