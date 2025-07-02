package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.androidtemplate.R

@Composable
fun BalanceTaskInfoComposable(
    availableBalance: String,
    availableGems: String,
    points: String
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text("Available Balance", fontSize = 12.sp, color = Color.Gray)
            Text(availableBalance, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Available Gems", fontSize = 12.sp, color = Color.Gray)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.gems),
                    contentDescription = "Gems",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "${availableGems ?: 0}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
            Text("${(String.format("%.3f", availableGems.toFloat()/1000))} KD", fontSize = 12.sp, color = Color.Gray)
        }
        Column(horizontalAlignment = Alignment.End) {

            Text("Points", fontSize = 12.sp, color = Color.Gray)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.points),
                    contentDescription = "Points",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "${points}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun TaskTypeOption(
    icon: Painter,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    ) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = icon,
            contentDescription = label,
            modifier = Modifier.size(100.dp).padding(4.dp)
        )
        Text(
            text = label,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) Color(0xFF000000) else Color.Gray,
            fontSize = 20.sp,
            )
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF000000))
        )
    }
}

@Composable
fun LabeledInput(label: String, value: String, keyboardOptions: KeyboardOptions = KeyboardOptions.Default, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .height(65.dp)
            .fillMaxWidth(0.85f)
            .clip(RoundedCornerShape(16.dp)),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFFFFFFF),
            unfocusedBorderColor = Color.LightGray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        shape = RoundedCornerShape(50.dp),
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun GradientSendButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(0.85f)
            .clip(RoundedCornerShape(30.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFE3692C), Color(0xFF8A30D5))
                )
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text("Send", color = Color.White, fontSize = 16.sp)
    }
}