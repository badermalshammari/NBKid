package com.example.androidtemplate.ui.screens

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.ui.composables.FloatUp
import com.example.androidtemplate.ui.composables.LoadingIndicator
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.cheque_android.ui.composables.TypingText

@Composable
fun LoginScreen(viewModel: NBKidsViewModel, navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage
    val user = viewModel.user
    val token = viewModel.token

    LaunchedEffect(token, user, isLoading) {
        if (!token.isNullOrBlank() && user != null && !isLoading) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

    if (isLoading && user == null) {
        LoadingIndicator()
        return
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF2B5D84)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxSize()) {

                Image(
                    painter = painterResource(id = R.drawable.loginpage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().size(30.dp)
                )
                FloatUp(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 200.dp)
                            .size(width = 370.dp, height = 450.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0x99FFFFFF))
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(100.dp))
                    FloatUp(
                        modifier = Modifier.size(180.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.defualt_avatar),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                    TypingText(
                        text = "Welcome Back..",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color(0xFF353D44)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        placeholder = { Text("   USERNAME", color = Color.Gray, fontSize = 13.sp) },
                        modifier = Modifier.fillMaxWidth()
                            .padding(15.dp)
                            .size(width = 50.dp, height = 50.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF3875A7),
                            unfocusedBorderColor = Color.LightGray,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            cursorColor = Color.Black
                        ),
                        shape = RoundedCornerShape(50.dp)
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("   PASSWORD", color = Color.Gray, fontSize = 13.sp) },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                            .padding(15.dp)
                            .size(width = 50.dp, height = 50.dp),

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF3875A7),
                            unfocusedBorderColor = Color.LightGray,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            cursorColor = Color.Black
                        ),
                        shape = RoundedCornerShape(50.dp)

                    )

                    Spacer(modifier = Modifier.height(60.dp))

                    Box(
                        modifier = Modifier
                            .size(width = 300.dp, height = 50.dp)
                            .clip(RoundedCornerShape(50))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Color(0xFF3875A7), Color(0xFF264B6D))
                                )
                            )
                            .clickable { viewModel.login(username, password) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Login", color = Color.White, fontSize = 16.sp)
                    }

                    if (!errorMessage.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(errorMessage, color = Color.Red)
                    }


                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        "Don't have an account? Sign up?",
                        color = Color(0xFFB7B7B7),
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Register.route)
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )



                    Spacer(modifier = Modifier.height(60.dp))

                    Text("@NBKidz Bank",
                        color = Color(0xFF353D44),
                        fontWeight = FontWeight.Black)

                }
            }
        }
    }
}