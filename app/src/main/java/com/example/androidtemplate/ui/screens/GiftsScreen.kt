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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.foundation.lazy.LazyColumn


@Composable
fun GiftsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onGiftClick: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ) {
        item {
            //Spacer(modifier = Modifier.height(40.dp))

            // Navigation Header
            GiftsNavigationHeader(onBackClick = onBackClick)

            Spacer(modifier = Modifier.height(40.dp))

            // Account Info
            GiftsAccountInfo()


            // Credit Card Section
            PaymentCard()


            // Balance and Stats Row
            BalanceInformation()


            // Search & Filter Section
            SearchFilterSection()

            Spacer(modifier = Modifier.height(32.dp))
        }

        // Gift Items Grid
        item {
            GiftItemsGrid(onGiftClick = onGiftClick)
        }
    }
}

@Composable
fun GiftsNavigationHeader(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back Arrow
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFF4A90E2), CircleShape)
                .clickable { onBackClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        // Gifts Title
        Text(
            "Gifts",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        // Notification Icon
        Box(
            modifier = Modifier
                .size(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = Color.Gray,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun GiftsAccountInfo() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Khaled Account",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            "(1234567890)",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}


@Composable
fun SearchFilterSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color(0xFFF5F5F5))
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color(0xFF4A90E2),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Search & Filter",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
            }

            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = "Filter",
                tint = Color(0xFF4A90E2),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun GiftItemsGrid(onGiftClick: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Gift Item 1
        GiftItem(
            emoji = "🧸",
            price = "3000",
            onClick = { onGiftClick("toys") }
        )

        // Gift Item 2
        GiftItem(
            emoji = "🦄",
            price = "2990",
            onClick = { onGiftClick("unicorns") }
        )

        // Gift Item 3
        GiftItem(
            emoji = "🔫",
            price = "10000",
            onClick = { onGiftClick("toy_gun") }
        )
    }
}

@Composable
fun GiftItem(
    emoji: String,
    price: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                emoji,
                fontSize = 60.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(Color(0xFF10B981), CircleShape)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                price,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GiftsScreenPreview() {
    MaterialTheme {
        GiftsScreen()
    }
}