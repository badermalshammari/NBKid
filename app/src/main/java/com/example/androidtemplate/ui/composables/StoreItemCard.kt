package com.example.androidtemplate.ui.composables

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.androidtemplate.data.dtos.ChildStoreItemDto
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
    var showConfirmDialog by remember { mutableStateOf(false) }

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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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
                fontSize = 18.sp,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.height(6.dp))

            // زر الطلب
            Button(
                onClick = {
                    showConfirmDialog = true
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
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }

    // Dialog التأكيد
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Are you sure?") },
            text = { Text("Do you want to place this order?") },
            confirmButton = {
                TextButton(onClick = {
                    showConfirmDialog = false
                    if (child?.childId != null) {
                        walletViewModel.orderItem(
                            childId = child.childId,
                            itemId = item.id,
                            onSuccess = {
                                Toast.makeText(context, "Order Completed!", Toast.LENGTH_SHORT).show()
                            },
                            onError = { msg ->
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                            }
                        )
                    }
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}