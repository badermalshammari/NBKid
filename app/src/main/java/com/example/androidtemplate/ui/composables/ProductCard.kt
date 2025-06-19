package com.example.androidtemplate.ui.composables
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.androidtemplate.R
//@Composable
//fun ProductCard(
//    imageUrl: String,
//    productName: String,
//    productType: String,
//    price: Int,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
//
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(16.dp)
//                .height(IntrinsicSize.Min),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Product Image
////            AsyncImage(
////                model = imageUrl,
////                contentDescription = null,
////                modifier = Modifier
////                    .size(80.dp)
////                    .clip(RoundedCornerShape(8.dp)),
////                placeholder = painterResource(id = R.drawable.placeholder_image),
////                error = painterResource(id = R.drawable.error_image)
////            )
//            AsyncImage(
//                model = imageUrl,
//                contentDescription = null,
//                modifier = Modifier
//                    .size(80.dp)
//                    .clip(RoundedCornerShape(8.dp)),
//                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
//                error = painterResource(id = R.drawable.ic_launcher_foreground)
//            )
//
//
//            Spacer(modifier = Modifier.width(16.dp))
//
//            // Product Details Column
//            Column(
//                modifier = Modifier.weight(1f),
//                verticalArrangement = Arrangement.SpaceBetween
//            ) {
//                // Product Name and Type
//                Column {
//                    Text(
//                        text = productName,
//                        style = MaterialTheme.typography.titleMedium,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Text(
//                        text = productType,
//                        style = MaterialTheme.typography.bodySmall,
//                        color = Color.Gray
//                    )
//                }
//
//                // Price Section
//                Icon(
//                    imageVector = Icons.Filled.AttachMoney,
//                    contentDescription = "Coin Icon",
//                    tint = Color(0xFF00B8D4)
//                )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(
//                        text = price.toString(),
//                        style = MaterialTheme.typography.bodyMedium,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//            }
//
//            // Get It Button
//        Box(
//            modifier = Modifier
//                .height(40.dp)
//                .width(100.dp)
//                .clip(RoundedCornerShape(20.dp))
//                .background(
//                    brush = Brush.horizontalGradient(
//                        colors = listOf(Color(0xFFC778DD), Color(0xFFFC6096))
//                    )
//                )
//                .clickable { onClick() },
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = "Get it",
//                color = Color.White,
//                style = MaterialTheme.typography.bodyMedium
//            )
//        }
//        }
//    }


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

                        // Price with coin icon
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Custom coin icon (you can replace with your actual coin icon)
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        Color(0xFF10B981),
                                        RoundedCornerShape(4.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "💎",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White
                                )
                            }

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

// Usage example with sample data
@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    MaterialTheme {
        Column {
            ProductCard(
                productTitle = "The Full Closet",
                productSubtitle = "Boardgame",
                price = 3500,
                productImage = {
                    // Sample image placeholder - replace with your actual image
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF6366F1), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("📦", fontSize = 32.sp)
                    }
                },
                onGetItClick = { /* Handle click */ }
            )

            ProductCard(
                productTitle = "EggEggy Wawa",
                productSubtitle = "Mystery Gift",
                price = 1500,
                productImage = {
                    // Sample image placeholder - replace with your actual image
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF06B6D4), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🥚", fontSize = 32.sp)
                    }
                },
                onGetItClick = { /* Handle click */ }
            )

            ProductCard(
                productTitle = "Dog Car",
                productSubtitle = "Car",
                price = 2000,
                productImage = {
                    // Sample image placeholder - replace with your actual image
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFEF4444), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🚗", fontSize = 32.sp)
                    }
                },
                onGetItClick = { /* Handle click */ }
            )
        }
    }
}

// Alternative version with Image composable for actual images
@Composable
fun ProductCardWithImage(
    productTitle: String,
    productSubtitle: String,
    price: Int,
    imageRes: Int, // Resource ID for the image
    onGetItClick: () -> Unit = {},
    backgroundColor: Color = Color.White,
    modifier: Modifier = Modifier
) {
    ProductCard(
        productTitle = productTitle,
        productSubtitle = productSubtitle,
        price = price,
        productImage = {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = productTitle,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        },
        onGetItClick = onGetItClick,
        backgroundColor = backgroundColor,
        modifier = modifier
    )
}