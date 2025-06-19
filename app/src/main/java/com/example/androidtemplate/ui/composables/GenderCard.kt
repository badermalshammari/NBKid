package com.example.androidtemplate.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun GenderCard(name: String, @DrawableRes imageRes: Int, selected: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) Color(0xFFD8ECFF) else Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier
                    .size(80.dp)
            )
            RadioButton(
                selected = selected,
                onClick = {},
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = (-4).dp, y = 4.dp),
                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF005BBB))
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(name, fontWeight = FontWeight.Medium)
    }
}