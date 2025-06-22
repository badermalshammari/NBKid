package com.example.androidtemplate.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextAlign

@Composable
fun StoreItemDetailScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onGetItemClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header Section
        StoreItemDetailHeader()

        // Navigation Header with Back Arrow and Heart
        NavigationHeader(onBackClick = onBackClick)

        // Item Detail Content
        StoreItemDetailContent(
            modifier = Modifier.weight(1f),
            onGetItemClick = onGetItemClick
        )

        // Bottom Navigation
        BottomNavigationBarDetail()
    }
}

@Composable
fun StoreItemDetailHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // User Avatar
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color(0xFFE3F2FD)),
            contentAlignment = Alignment.Center
        ) {
            // User avatar - replace with actual image
            Text("👦", fontSize = 40.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Gems and Points Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Available Gems
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Available Gems",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(Color(0xFF10B981), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "3000",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    "= 3,000 KD",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            // Points
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Points",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(Color(0xFFFFA500), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "48307",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationHeader(onBackClick: () -> Unit) {
    Column {
        // Gradient divider
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFFF6B35),
                            Color(0xFFFFA500),
                            Color(0xFFFFD700)
                        )
                    )
                )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Navigation Row with Back Arrow, Store Title, and Heart
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back Arrow
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFFF6B35), CircleShape)
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Store Title
            Text(
                "Store",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            // Heart Icon
            var isFavorite by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFE0E0E0), CircleShape)
                    .clickable { isFavorite = !isFavorite },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Red else Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun StoreItemDetailContent(
    modifier: Modifier = Modifier,
    onGetItemClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Product Image
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            // Product image placeholder - replace with actual image
            Text("📘", fontSize = 120.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Product Title
        Text(
            "The Full Closet",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Product Subtitle
        Text(
            "Boardgame",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        // Price and Get Button Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Price Section
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color(0xFF10B981), CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "3500",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            // Get It Button
            Button(
                onClick = onGetItemClick,
                modifier = Modifier
                    .height(56.dp)
                    .width(160.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF8B5CF6),
                                    Color(0xFFEF4444)
                                )
                            ),
                            shape = RoundedCornerShape(28.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Get it",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBarDetail() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF8B5CF6),
                        Color(0xFFEF4444)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            BottomNavItemDetail("📋", "Tasks", false)
            BottomNavItemDetail("🏆", "Leaderboard", false)
            BottomNavItemCenterDetail()
            BottomNavItemDetail("🎁", "Store", true)
            BottomNavItemDetail("❤️", "Wishlist", false)
        }
    }
}

@Composable
fun BottomNavItemDetail(icon: String, label: String, isSelected: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    if (isSelected) Color.White.copy(alpha = 0.2f) else Color.Transparent,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                icon,
                fontSize = 20.sp,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            label,
            fontSize = 10.sp,
            color = Color.White,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun BottomNavItemCenterDetail() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            // Z logo placeholder
            Text(
                "Z",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8B5CF6)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StoreItemDetailScreenPreview() {
    MaterialTheme {
        StoreItemDetailScreen()
    }
}