package com.example.androidtemplate.ui.composables


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.androidtemplate.data.dtos.Child

@Composable
fun ChildAvatarCard(
    child: Child,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    avatarSize: Int = 130,
    fontSize: Int = 3
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(140.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = child.avatarResId),
            contentDescription = child.name,
            modifier = Modifier
                .size(if (isSelected) avatarSize.dp else (avatarSize-50).dp)
                .clip(CircleShape)
                .border(
                    width = if (isSelected) fontSize.dp else (fontSize-3).dp,
                    color = if (isSelected) Color.LightGray else Color.White,
                    shape = CircleShape
                )
                .background(color = Color.White)
        )
        Text(
            child.name,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color.Black else Color.White,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}