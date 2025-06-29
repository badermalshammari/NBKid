package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.androidtemplate.R
import com.example.androidtemplate.data.dtos.Child
import com.example.androidtemplate.data.dtos.ChildDto
import com.example.androidtemplate.data.dtos.WalletResponseDto
import com.example.androidtemplate.ui.screens.getAvatarDrawable

@Composable
fun Header(child: Child, wallet: WalletResponseDto?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text("Welcome", fontSize = 20.sp, color = Color.Black)
            Text(
                text = child.name.replaceFirstChar { it.uppercaseChar() },
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                modifier = Modifier.size(100.dp),
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 6.dp
            ) {
                Image(
                    painter = painterResource(id = getAvatarDrawable(child.avatar)),
                    contentDescription = "Child Avatar",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.End
            ) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Available Gems",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.gems),
                            contentDescription = "Gems",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "${wallet?.gems ?: 0}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Text(
                        "= ${"%.3f".format((wallet?.gems ?: 0) / 1000.0)} KD",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Points",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.points),
                            contentDescription = "Points",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "${wallet?.pointsBalance ?: 0}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Box(
        modifier = Modifier
            .height(4.dp)
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    listOf(Color(0xFF8E2DE2), Color(0xFFF27121))
                )
            )
    )
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    val mockChild = Child(
        childId = 1L,
        name = "Zuzu",
        civilId = "123456789",
        birthday = "2015-05-10",
        gender = "FEMALE",
        avatar = "avatar_1",
        stats = "Excellent",
        avatarResId = R.drawable.zain
    )

    val mockWallet = WalletResponseDto(
        walletId = 1L,
        child = ChildDto(
            childId = 1L,
            name = "Zuzu",
            avatar = "avatar_1",
            stats = "Excellent"
        ),
        pointsBalance = 150,
        gems = 10010100
    )

    Header(child = mockChild, wallet = mockWallet)
}