package com.example.androidtemplate.ui.composables

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
import androidx.compose.ui.layout.ContentScale
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
import com.example.androidtemplate.ui.theme.JekoFontFamily

@Composable
fun Header(child: Child, wallet: WalletResponseDto?) {

    Box(modifier = Modifier
        .fillMaxWidth()){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(
                RoundedCornerShape(
                    bottomStart = 40.dp,
                    bottomEnd = 40.dp
                )
            )
    ) {
        Image(
            painter = painterResource(R.drawable.nbkidz_kuwait),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Spacer(Modifier.height(20.dp))
                Text("Welcome", fontSize = 20.sp, color = Color.White,fontFamily = JekoFontFamily,
                )
                Text(
                    text = child.name.replaceFirstChar { it.uppercaseChar() },
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = JekoFontFamily,
                    color = Color.White
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
                Spacer(Modifier.height(20.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Available Gems",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = JekoFontFamily,

                            )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.gems),
                                contentDescription = "Gems",
                                modifier = Modifier.size(24.dp),
                                )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                "${wallet?.gems ?: 0}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.White,
                                fontFamily = JekoFontFamily,

                                )
                        }
                        Text(
                            "= ${"%.3f".format((wallet?.gems ?: 0) / 1000.0)} KD",
                            fontSize = 10.sp,
                            color = Color.White,
                            fontFamily = JekoFontFamily,

                            )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Points",
                            color = Color.White,
                            fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold,
                            fontFamily = JekoFontFamily,

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
                                fontSize = 16.sp,
                                color = Color.White,
                                fontFamily = JekoFontFamily,

                                )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }

}