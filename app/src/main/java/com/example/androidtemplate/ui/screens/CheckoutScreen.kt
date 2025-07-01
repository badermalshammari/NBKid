package com.example.androidtemplate.ui.screens


import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import com.example.androidtemplate.R

@Preview
@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    onCompleteClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize().size(100.dp)) {
        Image(
            painter = painterResource(R.drawable.nbkidz_bgpng),
            contentDescription = "Background"
        )
    }
}

@Composable
fun CheckoutHeader() {
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
fun CheckoutContent(
    modifier: Modifier = Modifier,
    onCompleteClick: () -> Unit
) {
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

        // Checkout Title
        Text(
            "Checkout",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Table Headers
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Item",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(2f)
            )
            Text(
                "Qty",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Text(
                "Gems",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Order Item
        CheckoutItem()

        Spacer(modifier = Modifier.height(40.dp))

        // Divider Line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray.copy(alpha = 0.3f))
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Payment Summary
        PaymentSummary()

        Spacer(modifier = Modifier.weight(1f))

        // Complete Button
        Button(
            onClick = onCompleteClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 20.dp),
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
                    "Complete",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CheckoutItem() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Item Column (Image + Name)
        Column(
            modifier = Modifier.weight(2f)
        ) {
            // Product Image
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Text("📘", fontSize = 40.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Product Details
            Text(
                "The Full Closet",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Boardgame",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }

        // Quantity Column
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "1",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        // Price Column
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(Color(0xFF10B981), CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                "1500",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PaymentSummary() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Available Gems Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    "Available Gems",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    "= 3,000 KD",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color(0xFF10B981), CircleShape)
                )
                Spacer(modifier = Modifier.width(4.dp))
//                Text(
//                    "3000",
//                    style = MaterialTheme.typography.titleLarge,
//                    fontWeight = FontWeight.Bold
//                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Calculation Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Available Gems
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color(0xFF10B981), CircleShape)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "3000",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            // Minus Sign
            Text(
                "-",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            // Total Price
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(Color(0xFF10B981), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "1500",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    "= 1,500 KD",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 24.dp)
                )
            }

            // Equals Sign
            Text(
                "=",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            // After Redeem Gems
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(Color(0xFF10B981), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "1500",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    "= 1,500 KD",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Labels Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Available Gems",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                "Total Price",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                "After Redeem Gems",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun BottomNavigationBarCheckout() {
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
            BottomNavItemCheckout("📋", "Tasks", false)
            BottomNavItemCheckout("🏆", "Leaderboard", false)
            BottomNavItemCenterCheckout()
            BottomNavItemCheckout("🎁", "Store", true)
            BottomNavItemCheckout("❤️", "Wishlist", false)
        }
    }
}

@Composable
fun BottomNavItemCheckout(icon: String, label: String, isSelected: Boolean) {
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
fun BottomNavItemCenterCheckout() {
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
fun CheckoutScreenPreview() {
    MaterialTheme {
        CheckoutScreen()
    }
}