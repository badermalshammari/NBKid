package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.example.androidtemplate.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtemplate.data.dtos.OrderedItemDto

@Composable
fun OrderItemCard(order: OrderedItemDto) {

    val context = LocalContext.current

    val imageRes = remember(order.itemImageUrl) {
        val resId = context.resources.getIdentifier(
            order.itemImageUrl,
            "drawable",
            context.packageName
        )
        if (resId == 0) R.drawable.nbkidz_logo_white else resId
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
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = order.itemName,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // التفاصيل
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = order.itemName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth()){
                    Image(
                        painter = painterResource(id = R.drawable.gems),
                        contentDescription = "gems",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "${order.gemsCost}",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Text(
                    text = "${order.status}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = when (order.status) {
                        "COMPLETED" -> Color(0xFF158D47)
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