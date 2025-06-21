package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.data.dtos.Child
import com.example.androidtemplate.ui.composables.ChildAvatarCard

import com.example.androidtemplate.ui.composables.FloatUp
import com.example.androidtemplate.ui.composables.LoadingIndicator
import com.example.androidtemplate.viewmodels.NBKidsViewModel

@Composable
fun SelectKidScreen(viewModel: NBKidsViewModel, navController: NavController) {
    val user = viewModel.user
    val isLoading = viewModel.isLoading
    val listState = rememberLazyListState()

    var selectedIndex by remember { mutableStateOf(0) }

    if (isLoading) {
        LoadingIndicator()
        return
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF2B5D84)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Image(
                painter = painterResource(id = R.drawable.kidpage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            FloatUp(modifier = Modifier.fillMaxSize()) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        "LOGIN AS",
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(100.dp))


                    //KIDSSSSS ---------------------------


                    val children = listOf(
                        Child(1, "Bader", R.drawable.zain),
                        Child(2, "Mariam", R.drawable.zainah),
                        Child(3, "Ali", R.drawable.zain),
                        Child(4, "Fatima", R.drawable.zainah)
                    )

                    LazyRow(
                        state = listState,
                        flingBehavior = rememberSnapFlingBehavior(lazyListState = listState),
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        itemsIndexed(children) { index, child ->
                            val isSelected = index == selectedIndex

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.width(120.dp)
                            ) {
                                ChildAvatarCard(
                                    child = child,
                                    isSelected = isSelected,
                                    onClick = { selectedIndex = index }
                                )
                            }
                        }
                    }


                    //KIDSSSSS ---------------------------


                    Spacer(modifier = Modifier.height(100.dp))

                    Button(
                        onClick = {
                            selectedIndex.let {
                                println("Logging in as ${children[selectedIndex]}")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues(),
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(50.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(Color(0xFF8E2DE2), Color(0xFFF27121))
                                    ),
                                    shape = RoundedCornerShape(50)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Login", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }

                }
            }
        }
    }
}