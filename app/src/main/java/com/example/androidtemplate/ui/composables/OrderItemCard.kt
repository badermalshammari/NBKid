package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
        // Add all mappings you use
        else -> R.drawable.nbkidz_logo_white
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = order.itemName, fontWeight = FontWeight.Bold)
                Text(text = "Gems: ${order.gemsCost}")
                Text(text = "Status: ${order.status}")
                Text(
                    text = "Ordered: ${order.orderedAt.take(16).replace('T', ' ')}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}