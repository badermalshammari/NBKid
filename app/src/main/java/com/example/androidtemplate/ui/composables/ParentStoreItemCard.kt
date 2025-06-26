package com.example.androidtemplate.ui.composables

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import com.example.androidtemplate.data.dtos.ChildStoreItemDto
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtemplate.R

@Composable
fun ParentStoreItemCard(
    item: ChildStoreItemDto,
    imageResId: Int,
    onToggleHidden: (ChildStoreItemDto) -> Unit
) {
    Box(
        modifier = Modifier.clickable {
            item.isHidden = !item.isHidden
            onToggleHidden(item)
        }
    ) {
        if (item.isHidden == true) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xAA000000),
                                Color(0x66000000)
                            )
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
            )
        }

        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = item.globalItem.name,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.globalItem.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.Black
                )


            }
        }
    }
}