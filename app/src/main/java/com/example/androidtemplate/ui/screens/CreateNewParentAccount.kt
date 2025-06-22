package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides.Companion.Top
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtemplate.R
import com.example.androidtemplate.ui.composables.CreateAccountCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewParentAccountScreen() {
    var parentname by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            // Optional: Add an AppBar if needed
            TopAppBar(
                title = { Text("Create New Account") },
                modifier = Modifier.background(Color.White)
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .padding(padding), // Apply scaffold inner padding
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Remove the original "Create New Account" text if you added it in TopAppBar

                // Input fields
                OutlinedTextField(
                    value = parentname,
                    onValueChange = {parentname = it},
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = {username = it},
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {password = it},
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    isError = confirmPassword.isNotEmpty() && password != confirmPassword
                )
                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {phoneNumber = it},
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                if (confirmPassword.isNotEmpty() && password != confirmPassword) {
                    Text(
                        text = "Passwords do not match",
                        color = Color(0xFFD32F2F),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Card Design Preview
                Text(
                    text = "Card Design",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.align(Alignment.Start)
                )

                CardDesignPreviewForParent()
                Spacer(modifier = Modifier.height(8.dp))
                AddMyCardButtonForParent(onClick = {})
            }
        }
    )
}
@Composable
fun CardDesignPreviewForParent() {

    val listState = rememberLazyListState()
    val currentIndex by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex
        }
    }

    val cardImages = listOf(
        R.drawable.parentcard_1,
        R.drawable.parentcard_2,
        R.drawable.parentcard_3,
        R.drawable.parentcard_4
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFD8ECFF)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {

            cardImages.forEachIndexed { index, resId ->
                item {
                    CreateAccountCard(backgroundRes = resId)
                }
            }
        }

        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            cardImages.forEachIndexed { index, _ ->
                val isSelected = index == currentIndex
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(if (isSelected) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Color(0xFF004080) else Color.Gray.copy(alpha = 0.3f))
                )
            }
        }
    }
}
@Composable
fun AddMyCardButtonForParent(onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .height(40.dp)
            .width(150.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF3F51B5), Color(0xFF03A9F4))
                )
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Add My Card",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateNewParentAccountScreen() {
    MaterialTheme {
        CreateNewParentAccountScreen()
    }
}