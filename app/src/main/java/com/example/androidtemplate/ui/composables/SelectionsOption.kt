package com.example.androidtemplate.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SelectionOptionsComposable(
    selectedOption: MutableState<String>,
    firstIcon: Painter,
    firstText: String,
    secondIcon: Painter,
    secondText: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = firstIcon,
                contentDescription = "Gem Icon",
                modifier = Modifier.size(64.dp)
            )
            Text(text = firstText, fontSize = 16.sp)
            RadioButton(
                selected = selectedOption.value == "Gems",
                onClick = { selectedOption.value = "Gems" }
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = secondIcon,
                contentDescription = "Cash Icon",
                modifier = Modifier.size(64.dp)
            )
            Text(text = secondText, fontSize = 16.sp)
            RadioButton(
                selected = selectedOption.value == "Cash",
                onClick = { selectedOption.value = "Cash" }
            )
        }
    }
}