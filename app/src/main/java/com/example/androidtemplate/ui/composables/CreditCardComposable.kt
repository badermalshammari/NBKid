package com.example.androidtemplate.ui.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import com.example.androidtemplate.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtemplate.data.dtos.BankCardDto


@Composable
fun CreditCardComposable(card: BankCardDto?) {
    if (card == null) return

    val context = LocalContext.current

    val imageResId = remember(card.cardDesign) {
        context.resources.getIdentifier(card.cardDesign, "drawable", context.packageName)
    }
    val disabledimageResId = remember("${card.cardDesign}_disabled") {
        context.resources.getIdentifier("${card.cardDesign}_disabled", "drawable", context.packageName)
    }
    if (imageResId == 0) {
        Log.e("CreditCardComposable", "Invalid drawable resource for card design: ${card.cardDesign}")
        return
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
    ) {
        if(card.isActive==true) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }else{
            Image(
                painter = painterResource(id = disabledimageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize())
        }


        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${card.cardHolderName.uppercase()}\n(${card.accountNumber})",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 15.sp

            )
            if(card.isActive==true) {
                Text(
                    text = "**** **** **** ${card.cardNumber.takeLast(4)}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    fontSize = 25.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Column {
                        Text(
                            "VALID",
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text("${card.expiryMonth}/${card.expiryYear}", color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(60.dp))

                    Column {
                        Text(
                            "CVV",
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(card.cvv, color = Color.White)
                    }
                }
            }
        }
    }
}