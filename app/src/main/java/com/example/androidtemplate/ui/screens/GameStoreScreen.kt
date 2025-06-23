package com.example.androidtemplate.ui.screens




import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidtemplate.ui.composables.GameStoreHeader

@Composable
fun GameStoreScreen(
    modifier: Modifier = Modifier,
    onProductClick: (Product) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header Section
        GameStoreHeader()

        // Products List
        ProductsList(
            modifier = Modifier.weight(1f),
            onProductClick = onProductClick
        )

        // Bottom Navigation (if needed)
        // BottomNavigationBar()
    }
}

@Composable
fun ProductsList(
    modifier: Modifier = Modifier,
    onProductClick: (Product) -> Unit = {}
) {
    // TODO: Replace this sample data with your database
    val products = getSampleProducts()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductCard(
                productTitle = product.title,
                productSubtitle = product.subtitle,
                price = product.price,
                productImage = {
                    // Product image placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(product.imageColor, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(product.emoji, fontSize = 32.sp)
                    }
                },
                onGetItClick = { onProductClick(product) }
            )
        }
    }
}

// Your existing ProductCard composable goes here
@Composable
fun ProductCard(
    productTitle: String,
    productSubtitle: String,
    price: Int,
    productImage: @Composable () -> Unit, // Composable for the product image
    onGetItClick: () -> Unit = {},
    backgroundColor: Color = Color.White,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        // Gradient border background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF8B5CF6), // Purple
                            Color(0xFFEF4444)  // Red
                        )
                    )
                )
                .padding(2.dp) // Border thickness
        ) {
            // Inner content with white background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(backgroundColor)
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Product image section
                    Box(
                        modifier = Modifier
                            .size(80.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        productImage()
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Product info section
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = productTitle,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = productSubtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Price with gem icon
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Gem icon
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(Color(0xFF10B981), CircleShape)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = price.toString(),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Get it button
                        Button(
                            onClick = onGetItClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF8B5CF6)
                            ),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Text(
                                text = "Get it",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

// Data class for Product
data class Product(
    val id: Int,
    val title: String,
    val subtitle: String,
    val price: Int,
    val emoji: String,
    val imageColor: Color
)

// TODO: Replace this function with your actual database call
fun getSampleProducts(): List<Product> {
    return listOf(
        Product(
            id = 1,
            title = "The Full Closet",
            subtitle = "Boardgame",
            price = 3500,
            emoji = "📦",
            imageColor = Color(0xFF6366F1)
        ),
        Product(
            id = 2,
            title = "EggEggy Wawa",
            subtitle = "Mystery Gift",
            price = 1500,
            emoji = "🥚",
            imageColor = Color(0xFF06B6D4)
        ),
        Product(
            id = 3,
            title = "Dog Car",
            subtitle = "Car",
            price = 2500,
            emoji = "🚗",
            imageColor = Color(0xFFEF4444)
        ),
        Product(
            id = 4,
            title = "Magic Wand",
            subtitle = "Accessory",
            price = 1200,
            emoji = "🪄",
            imageColor = Color(0xFF10B981)
        ),
        Product(
            id = 5,
            title = "Super Ball",
            subtitle = "Toy",
            price = 800,
            emoji = "⚽",
            imageColor = Color(0xFFF59E0B)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun UGameStoreScreenPreview() {
    MaterialTheme {
        GameStoreScreen()
    }
}