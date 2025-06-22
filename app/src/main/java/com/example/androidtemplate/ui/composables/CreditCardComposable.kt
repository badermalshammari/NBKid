package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import com.example.androidtemplate.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androidtemplate.data.dtos.BankCardDto
import androidx.compose.ui.res.painterResource

@Composable
fun CreditCardComposable(card: BankCardDto?) {
    if (card == null) return

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(Color(0xFF0D47A1), Color(0xFF1976D2)) // Blue gradient
                )
            )
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "CARD NUMBER",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White
            )

            Text(
                text = "**** **** **** ${card.cardNumber.takeLast(4)}",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("VALID", color = Color.White, style = MaterialTheme.typography.labelSmall)
                    Text("${card.expiryMonth}/${card.expiryYear}", color = Color.White)
                }
                Column {
                    Text("CVV", color = Color.White, style = MaterialTheme.typography.labelSmall)
                    Text(card.cvv, color = Color.White)
                }
                // Placeholder for logo
                Image(
                    painter = painterResource(id = R.drawable.asset10),
                    contentDescription = "Card Logo",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}