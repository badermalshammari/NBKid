package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.viewmodels.NBKidsViewModel

@Composable
fun RegisterScreen(viewModel: NBKidsViewModel, navController: NavController) {
    val backgroundColor = Color(0xFF292F38)
    val textColor = Color.White
    val buttonColor  = Color(0xFF2ED2C0)

    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var acceptTerms by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Create Account", fontWeight = FontWeight.Bold, fontSize = 35.sp, color = textColor)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color(0xFFF5F4FA),
                unfocusedContainerColor = Color(0xFFF5F4FA),
                cursorColor = Color.Black,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color(0xFFF5F4FA),
                unfocusedContainerColor = Color(0xFFF5F4FA),
                cursorColor = Color.Black,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color(0xFFF5F4FA),
                unfocusedContainerColor = Color(0xFFF5F4FA),
                cursorColor = Color.Black,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color(0xFFF5F4FA),
                unfocusedContainerColor = Color(0xFFF5F4FA),
                cursorColor = Color.Black,
                focusedPlaceholderColor = Color.LightGray,
                unfocusedPlaceholderColor = Color.LightGray
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = acceptTerms, onCheckedChange = { acceptTerms = it },     colors = CheckboxDefaults.colors(
                checkedColor = buttonColor
            )
            )
            Spacer(modifier = Modifier.width(8.dp))
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

        val allValid = fullName.isNotBlank() && username.isNotBlank() && password.isNotBlank() &&
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
            Text(if (isLoading) "Signing up..." else "Sign up", color = textColor)
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

@Composable
fun RoleOption(roleValue: String, title: String, subtitle: String, selected: String, onSelect: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .selectable(
                selected = selected == roleValue,
                onClick = { onSelect(roleValue) }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected == roleValue,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF2DD4C0) // اللون اللي تبيه
            ),
            onClick = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = title, style = MaterialTheme.typography.titleMedium, color = Color.White)
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = Color.LightGray)
        }
    }
}