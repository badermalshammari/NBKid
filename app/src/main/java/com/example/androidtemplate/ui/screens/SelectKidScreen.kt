package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidtemplate.R
import com.example.androidtemplate.ui.composables.ChildAvatarCard
import com.example.androidtemplate.ui.composables.FloatUp
import com.example.androidtemplate.ui.composables.LoadingIndicator
import com.example.androidtemplate.viewmodels.NBKidsViewModel

@Composable
fun SelectKidScreen(viewModel: NBKidsViewModel, navController: NavController) {
    val listState = rememberLazyListState()
    var selectedIndex by remember { mutableStateOf(0) }
    val children = viewModel.children
    val isLoading = viewModel.isLoading

    LaunchedEffect(Unit) {
        viewModel.fetchChildren()
    }

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
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(100.dp))

                    LazyRow(
                        state = listState,
                        flingBehavior = rememberSnapFlingBehavior(listState),
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
                                    child = child.copy(
                                        avatarResId = getAvatarDrawable(child.avatar)
                                    ),
                                    isSelected = isSelected,
                                    onClick = { selectedIndex = index }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(100.dp))

                    Button(
                        onClick = {
                            val selectedChild = children.getOrNull(selectedIndex)
                            println("Logging in as $selectedChild")
                            // TODO: store child in ViewModel or navigate as needed
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
                            Text("Login", color = Color.White, fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }
}

// Maps avatar filename from backend to drawable
fun getAvatarDrawable(avatar: String?): Int {
    return when (avatar?.lowercase()) {
        "zainah.png" -> R.drawable.zainah
        else -> R.drawable.zain
    }
}