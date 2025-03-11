// SignUpScreen.kt
package com.snake.snakes2.ui.signup

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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.snake.snakes2.R
import com.snake.snakes2.ui.theme.Snakes2Theme
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController, onSignUpSuccess: () -> Unit, onBackToLogin: () -> Unit) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var mail by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val inlandersFont = remember {
        try {
            FontFamily(Font(R.font.inlanders_font, FontWeight.Normal))
        } catch (e: Exception) {
            FontFamily.Default
        }
    }

    val db = FirebaseFirestore.getInstance()

    Snakes2Theme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 110.dp),
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
                            .wrapContentHeight()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "SIGN UP",
                            color = Color.White,
                            fontFamily = inlandersFont,
                            fontSize = 32.sp,
                            letterSpacing = 4.sp,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )

                        val textFieldSizeModifier = Modifier
                            .width(300.dp)
                            .height(55.dp)

                        Text("USERNAME", color = Color.White, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(5.dp))
                        StyledTextField(username, "Enter a username") {
                            val it = ""
                            username = it
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text("EMAIL", color = Color.White, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(5.dp))
                        StyledTextField(mail, "Enter your email") {
                            val it = ""
                            mail = it
                        }

                        Spacer(modifier = Modifier.height(15.dp))

                        Text("PASSWORD", color = Color.White, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(5.dp))
                        PasswordField(
                            "Enter a password",
                            password,
                            { password = it },
                            passwordVisible,
                            { passwordVisible = it }
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        Text("CONFIRM PASSWORD", color = Color.White, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(5.dp))
                        PasswordField(
                            "Confirm your password",
                            confirmPassword,
                            { confirmPassword = it },
                            confirmPasswordVisible,
                            { confirmPasswordVisible = it }
                        )
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
                            if (username.isBlank() || mail.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                                Toast.makeText(context, "All fields are required!", Toast.LENGTH_SHORT).show()
                            } else if (password != confirmPassword) {
                                Toast.makeText(context, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                            } else {
                                val user = hashMapOf(
                                    "Username" to username,
                                    "email" to mail,
                                    "password" to password
                                )

                                db.collection("users")
                                    .add(user)
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "User Registered!", Toast.LENGTH_SHORT).show()
                                        onSignUpSuccess()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        },
                        modifier = Modifier.fillMaxSize(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Text("REGISTER", color = Color.White, fontSize = 18.sp)
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Already have an account? Log in",
                    color = Color.White,
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        // Instead of creating a new LoginScreen instance, pop back to it
                        navController.popBackStack("loginScreen", inclusive = false)
                    }
                )

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledTextField(value: String, placeholder: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .width(300.dp)
            .height(55.dp)
            .background(Color(0xff5e6b38), shape = RoundedCornerShape(8.dp))
            .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp)),
        singleLine = true,
        placeholder = { Text(placeholder, color = Color.White.copy(alpha = 0.6f)) },
        colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    passwordVisible: Boolean,
    onVisibilityChange: (Boolean) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .width(300.dp)
            .height(55.dp)
            .background(Color(0xff5e6b38), shape = RoundedCornerShape(8.dp))
            .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp)),
        singleLine = true,
        placeholder = { Text(placeholder, color = Color.White.copy(alpha = 0.6f)) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible) R.drawable.visibilityon else R.drawable.visibilityoff

            IconButton(onClick = { onVisibilityChange(!passwordVisible) }) {
                Icon(
                    painter = painterResource(id = image),
                    contentDescription = "Toggle Password Visibility",
                    tint = Color.White
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
    )
}
