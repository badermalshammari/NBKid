package com.example.androidtemplate.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp

@Composable
fun LeaderboardScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header Section
        LeaderboardHeader()

        // Leaderboard Content
        LeaderboardContent(
            modifier = Modifier.weight(1f)
        )

        // Bottom Navigation
        //BottomNavigationBar()
    }
}

@Composable
fun LeaderboardHeader() {
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
                .background(Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
        ) {
            // User avatar placeholder - replace with actual image
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
                    "+ 3,000 KD",
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
fun LeaderboardContent(modifier: Modifier = Modifier) {
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

        // Leaderboard Title
        Text(
            "Leaderboard",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Monthly/All-Time Toggle
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            ToggleButton1(
                text = "Monthly",
                isSelected = false,
                onClick = { }
            )
//            ToggleButton(
//                text = "All-Time",
//                isSelected = true,
//                onClick = { }
//            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Podium Section
        PodiumSection()

        Spacer(modifier = Modifier.height(24.dp))

        // Leaderboard List
        LeaderboardList()
    }
}

@Composable
fun ToggleButton1(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .background(
                color = if (isSelected) Color(0xFF1E40AF) else Color.Transparent,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.Gray,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun PodiumSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        // Second Place
        PodiumItem(
            rank = "2",
            name = "Bader",
            points = "50587",
            podiumColor = Color(0xFFFF6B35),  //0xFFFF6B35
            height = 80.dp,
            avatar = "👤"
        )

        // First Place
        PodiumItem(
            rank = "1",
            name = "Fatemah",
            points = "74999",
            podiumColor = Color(0xFF8B5CF6),
            height = 120.dp,
            avatar = "👦",
            showCrown = true
        )

        // Third Place
        PodiumItem(
            rank = "3",
            name = "Khaled",
            points = "48307",
            podiumColor = Color(0xFF10B981),
            height = 60.dp,
            avatar = "👦"
        )
    }
}

@Composable
fun PodiumItem(
    rank: String,
    name: String,
    points: String,
    podiumColor: Color,
    height: Dp,
    avatar: String,
    showCrown: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Crown for first place
        if (showCrown) {
            Text("👑", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(4.dp))
        }

        // Avatar
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
        ) {
            Text(avatar, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Name and Points
        Text(
            name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            points,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Podium
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(height)
                .background(
                    podiumColor,
                    RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                rank,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun LeaderboardList() {
    // Sample data for remaining positions
    val leaderboardData = listOf(
        LeaderboardItem("👦", "Fatemah", "74999"),
        LeaderboardItem("👤", "Bader", "50587"),
        LeaderboardItem("👦", "Khaled", "48307")
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(leaderboardData) { index, item ->
            LeaderboardRowItem(
                rank = (index + 1).toString(),
                avatar = item.avatar,
                name = item.name,
                points = item.points
            )
        }
    }
}


@Composable
fun LeaderboardRowItem(
    rank: String,
    avatar: String,
    name: String,
    points: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F9FA), RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Rank
        Text(
            rank,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Avatar
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
        ) {
            Text(avatar, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Name
        Text(
            name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        // Points with coin icon
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Color(0xFFFFA500), CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                points,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

//@Composable
//fun BottomNavigationBar() {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(60.dp)
//            .background(
//                brush = Brush.horizontalGradient(
//                    colors = listOf(
//                        Color(0xFF8B5CF6),
//                        Color(0xFFEF4444)
//                    )
//                )
//            ),
//        contentAlignment = Alignment.Center
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            BottomNavItem("📊", true)
//            BottomNavItem("🏆", false)
//            BottomNavItem("🎯", false)
//            BottomNavItem("⚙️", false)
//        }
//    }
//}

@Composable
fun BottomNavItem(icon: String, isSelected: Boolean) {
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
}

// Data class for leaderboard items
data class LeaderboardItem(
    val avatar: String,
    val name: String,
    val points: String
)

@Preview(showBackground = true)
@Composable
fun LeaderboardScreenPreview() {
    MaterialTheme {
        LeaderboardScreen()
    }
}