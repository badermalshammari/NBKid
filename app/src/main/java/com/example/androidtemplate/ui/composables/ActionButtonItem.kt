package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtemplate.R

@Composable
fun ActionButtonItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    backgroundColor: Color =Color(0xFFFFFFFF)
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0x403875A7), Color(0x403875A7))
                    )
                )
                .size(width = 80.dp, height = 80.dp)
                .padding(16.dp)
                .clickable { onClick() }
        ) {
            if (label == "Control") {
                Image(
                    painter = painterResource(id = R.drawable.zuzu_logo_blue),
                    contentDescription = label,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                )
            } else {
                Icon(
                    icon,
                    contentDescription = label,
                    tint = Color(0xFF2E5D85),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(label, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = backgroundColor)
    }
}