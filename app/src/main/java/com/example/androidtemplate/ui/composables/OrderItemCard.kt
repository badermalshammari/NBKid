package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.androidtemplate.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtemplate.data.dtos.OrderedItemDto

@Composable
fun OrderItemCard(order: OrderedItemDto) {
    val imageRes = when (order.itemImageUrl) {
        "toy_1" -> R.drawable.toy_1
        "toy_5" -> R.drawable.toy_5
        else -> R.drawable.nbkidz_logo_white
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // صورة الطلب
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = order.itemName,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // التفاصيل
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = order.itemName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Gems: ${order.gemsCost}",
                    fontSize = 13.sp,
                    color = Color(0xFF6C63FF),
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "Status: ${order.status}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = when (order.status) {
                        "COMPLETED" -> Color(0xFF2ECC71)
                        "PENDING" -> Color(0xFFFFC107)
                        else -> Color.Gray
                    }
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Ordered at: ${order.orderedAt.take(16).replace('T', ' ')}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}