package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.ui.composables.FloatUp
import com.example.androidtemplate.ui.composables.LoadingIndicator
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.ui.composables.TypingText

@Composable
fun HomeScreen(viewModel: NBKidsViewModel, navController: NavController) {
    val user = viewModel.user
    val isLoading = viewModel.isLoading

    if (isLoading) {
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
                    Image(
                        painter = painterResource(id = R.drawable.defualt_avatar),
                        contentDescription = null,
                        modifier = Modifier.size(180.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))


                    TypingText(
                        text = "Welcome ${user?.username} ..".uppercase(),
                        fontWeight = FontWeight.Black,
                        fontSize = 25.sp,
                        color = Color(0xFF353D44)
                    )
                    Spacer(modifier = Modifier.height(120.dp))

                    Text(
                        text = "DO YOU WANT TO LOGIN AS",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF353D44),
                        modifier = Modifier.padding(8.dp).align(alignment = Alignment.CenterHorizontally),
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Parent Button
                        Box(
                            modifier = Modifier
                                .size(width = 300.dp, height = 50.dp)
                                .clip(RoundedCornerShape(50))
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(Color(0xFF1E3C72), Color(0xFF2A6DA9))
                                    )
                                )
                                .clickable { navController.navigate(Screen.SignupFailureScreen.route) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("PARENT", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Medium)
                        }

                        // Child Button
                        Box(
                            modifier = Modifier
                                .size(width = 300.dp, height = 50.dp)
                                .clip(RoundedCornerShape(50))
                                .background(
                                    brush = Brush.horizontalGradient(
                                        listOf(Color(0xFF8E2DE2), Color(0xFFF27121))
                                    )
                                )
                                .clickable { navController.navigate(Screen.SelectKidScreen.route) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("CHILD", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Medium)
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .size(40.dp)
                                    .padding(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.nbkidz_logo_grey), // <- غيرها حسب اسم شعارك
                                    contentDescription = "Zuzu Icon",
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }
                    }

                }

            }
        }
    }
}