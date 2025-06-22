package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.ui.composables.FloatDown
import com.example.androidtemplate.ui.composables.FloatUp
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.ui.composables.TypingText


@Composable
fun RegisterScreen(viewModel: NBKidsViewModel, navController: NavController) {

    val textColor = Color(0xFF353D44)
    val buttonColor  = Color(0xFF326B98)

    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var acceptTerms by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
                FloatDown(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 200.dp)
                            .size(width = 370.dp, height = 450.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0x99FFFFFF))
                    )
                }
                FloatUp(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        TypingText(
                            "Create Account",
                            fontWeight = FontWeight.Bold,
                            fontSize = 35.sp,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(50.dp))

                        OutlinedTextField(
                            value = fullName,
                            onValueChange = { fullName = it },
                            placeholder = { Text("Name", color = Color.Gray, fontSize = 13.sp) },
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
                            value = username,
                            onValueChange = { username = it },
                            placeholder = {
                                Text(
                                    "Username",
                                    color = Color.Gray,
                                    fontSize = 13.sp
                                )
                            },
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
                            placeholder = {
                                Text(
                                    "Password",
                                    color = Color.Gray,
                                    fontSize = 13.sp
                                )
                            },
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


                        OutlinedTextField(
                            value = number,
                            onValueChange = { number = it },
                            placeholder = {
                                Text(
                                    "Phone Number",
                                    color = Color.Gray,
                                    fontSize = 13.sp
                                )
                            },
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

                        Spacer(modifier = Modifier.height(50.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = acceptTerms,
                                onCheckedChange = { acceptTerms = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = buttonColor
                                )
                            )
                            Text(
                                text = "I accept NBKids Terms & Conditions and Privacy Policy",
                                color = textColor,
                                style = MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.Underline),
                                modifier = Modifier.clickable {
                                    navController.navigate(Screen.PolicyScreen.route)
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        val allValid =
                            fullName.isNotBlank() && username.isNotBlank() && password.isNotBlank() &&
                                    acceptTerms && number.isNotBlank()

                        if (errorMessage != null) {
                            Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        Button(
                            onClick = {
                                isLoading = true
                                viewModel.register(
                                    name = fullName,
                                    phone = number,
                                    username = username,
                                    password = password,
                                    onSuccess = {
                                        isLoading = false
                                        navController.navigate(Screen.SignupSuccess.route)
                                    },
                                    onError = {
                                        isLoading = false
                                        errorMessage = it
                                    }
                                )
                            },
                            enabled = allValid && !isLoading,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                        ) {
                            Text(if (isLoading) "Signing up..." else "Sign up", color = Color.White)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            "Already have an account? Sign in",
                            color = textColor,
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.Login.route)
                            }
                        )
                    }
                }
            }
        }
    }
}