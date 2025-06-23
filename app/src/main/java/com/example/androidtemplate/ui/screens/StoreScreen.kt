package com.example.androidtemplate.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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

@Composable
fun StoreScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header Section
        StoreHeader()

        // Store Content
        StoreContent(
            modifier = Modifier.weight(1f)
        )

        // Bottom Navigation
        BottomNavigationBarDetail()
    }
}

@Composable
fun StoreHeader() {
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
fun StoreContent(modifier: Modifier = Modifier,onBackClick: () -> Unit = {}) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
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


        // Store Title with Arrow
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Navigation Header with Back Arrow and Heart
            //NavigationHeader(onBackClick = onBackClick)
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



            Spacer(modifier = Modifier.width(16.dp))

            Text(
                "Store",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Store Items Grid
        StoreItemsGrid()
    }
}

@Composable
fun StoreItemsGrid() {
    val storeItems = listOf(
        StoreItem("🏠", "99900", true),
        StoreItem("📘", "3500", false),
        StoreItem("🎮", "15000", false),
        StoreItem("🏛️", "99900", false),
        StoreItem("🤖", "2500", true),
        StoreItem("🚗", "15000", false)
    )


    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(storeItems) { item ->
            StoreItemCard(item = item)
        }
    }
}

@Composable
fun StoreItemCard(item: StoreItem) {
    var isFavorite by remember { mutableStateOf(item.isFavorite) }

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Favorite Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Red else Color.Gray,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { isFavorite = !isFavorite }
                )
            }

            // Item Image (using emoji as placeholder)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    item.emoji,
                    fontSize = 40.sp
                )
            }

            // Price
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color(0xFF10B981), CircleShape)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    item.price,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun BottomNavItem(icon: String, label: String, isSelected: Boolean) {
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
fun BottomNavItemCenter() {
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

// Data class for store items
data class StoreItem(
    val emoji: String,
    val price: String,
    val isFavorite: Boolean = false
)

@Preview(showBackground = true)
@Composable
fun StoreScreenPreview() {
    MaterialTheme {
        StoreScreen()
    }
}

