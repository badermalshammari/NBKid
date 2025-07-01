package com.example.androidtemplate.ui.composables

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import com.example.androidtemplate.data.dtos.ChildStoreItemDto
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtemplate.R
import com.example.androidtemplate.data.dtos.Child
import com.example.androidtemplate.ui.theme.JekoFontFamily
import com.example.androidtemplate.viewmodels.WalletViewModel

@Composable
fun StoreItemCard(
    item: ChildStoreItemDto,
    imageResId: Int,
    canAfford: Boolean,
    walletViewModel: WalletViewModel,
    child: Child?
) {
    val context = LocalContext.current
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
            // صورة المنتج
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = item.globalItem.name,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // اسم المنتج
            Text(
                text = item.globalItem.name,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                fontFamily = JekoFontFamily,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(6.dp))

            // زر الطلب + السعر
            Button(
                onClick = {
                    if (child?.childId != null) {
                        walletViewModel.orderItem(
                            childId = child.childId,
                            itemId = item.id,
                            onSuccess = {
                                Toast.makeText(context, "Order Completed !", Toast.LENGTH_SHORT).show()
                            },
                            onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_LONG).show() }
                        )
                    }
                },
                shape = RoundedCornerShape(25),
                enabled = canAfford,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50))
                    .background(
                        brush = Brush.horizontalGradient(
                            if (canAfford)
                                listOf(Color(0xFF8E44AD), Color(0xFFE74C3C))
                            else
                                listOf(Color.Gray, Color.LightGray)
                        )
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.gems),
                        contentDescription = "gems",
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${item.globalItem.costInGems}",
                        color = Color.White,
                        fontFamily = JekoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}