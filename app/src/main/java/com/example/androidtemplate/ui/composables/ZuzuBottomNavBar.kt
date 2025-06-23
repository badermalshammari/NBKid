package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ZuzuBottomNavBar(selected: String, onItemSelected: (String) -> Unit) {
    val items = listOf("Home", "Tasks", "Store", "Leaderboard")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.PlaylistAdd,
        Icons.Default.CardGiftcard,
        Icons.Default.Leaderboard
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(80.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(40.dp),
                    clip = false
                )
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF8E44AD), Color(0xFFE74C3C))
                    ),
                    shape = RoundedCornerShape(40.dp)
                )
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                items.forEachIndexed { index, item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onItemSelected(item) }
                    ) {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = item,
                            tint = if (item == selected) Color.White else Color(0xFF6D214F),
                            modifier = Modifier.size(28.dp)
                        )
                        Text(
                            text = item,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (item == selected) Color.White else Color(0xFF6D214F)
                        )
                    }
                }
            }
        }

        // الزر العائم في النص
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-20).dp)
                .background(Color.White, CircleShape)
                .size(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(com.example.androidtemplate.R.drawable.nbkidz_logo),
                modifier = Modifier.size(40.dp),
                contentDescription = "Logo"
            )
        }
    }
}