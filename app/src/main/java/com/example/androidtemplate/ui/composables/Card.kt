package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CreditCardDisplay(
    cardNumber: String,
    validDate: String,
    cvv: String,
    logoRes: Int,
    backgroundRes: Int
) {
    Box(
        modifier = Modifier
            .width(300.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(20.dp))
            .padding(5.dp)
    ) {

        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = "Card Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
        )
        Column(modifier =
            Modifier
                .fillMaxSize()
                .padding(15.dp)
        )

        {
            Text("CARD NUMBER", color = Color.White, fontSize = 12.sp)
            Text(cardNumber, color = Color.White, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text("VALID", color = Color.White, fontSize = 12.sp)
                    Text(validDate, color = Color.White, fontSize = 14.sp)
                }
                Column {
                    Text("CVV", color = Color.White, fontSize = 12.sp)
                    Text(cvv, color = Color.White, fontSize = 14.sp)
                }
                Icon(
                    painter = painterResource(id = logoRes),
                    contentDescription = "Card Logo",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Unspecified
                )
            }
        }
    }
}


@Composable
fun CreateAccountCard(backgroundRes: Int) {
    Box(
        modifier = Modifier
            .width(300.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(20.dp))
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = "Card Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
        )
    }
}