package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx .compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidtemplate.R
import com.example.androidtemplate.ui.composables.CreateAccountCard
import com.example.androidtemplate.ui.composables.GenderCard


@Composable
fun CreateNewAccountScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(

            text = "Create New Account",
            style = MaterialTheme.typography.headlineSmall,//MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Input fields
        OutlinedTextField(

            value = "", onValueChange = {},
            label = { Text("Kid Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = "", onValueChange = {},
            label = { Text("Civil Id") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = "", onValueChange = {},
            label = { Text("Birthday") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Gender selection
        Row(


            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)

        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GenderCard(
                        name = "Zainah",
                        imageRes = R.drawable.zainah,
                        selected = true
                    )
                    GenderCard(
                        name = "Zain",
                        imageRes = R.drawable.zain,
                        selected = false
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Card Design Preview
        Text(
            text = "Card Design",
            style = MaterialTheme.typography.headlineSmall,//MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.align(Alignment.Start)
        )

        CardDesignPreview()
        Spacer(modifier = Modifier.height(8.dp))
        AddMyCardButton(onClick = {})
    }
}
@Composable
fun CardDesignPreview() {

    val listState = rememberLazyListState()
    val currentIndex by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex
        }
    }

    val cardImages = listOf(
        R.drawable.kidcard_1,
        R.drawable.kidcard_2,
        R.drawable.kidcard_3,
        R.drawable.kidcard_4
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFD8ECFF)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            cardImages.forEachIndexed { index, resId ->
                item {
                    CreateAccountCard(backgroundRes = resId)
                }
            }
        }

        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            cardImages.forEachIndexed { index, _ ->
                val isSelected = index == currentIndex
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(if (isSelected) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Color(0xFF004080) else Color.Gray.copy(alpha = 0.3f))
                )
            }
        }
    }
}
@Composable
fun AddMyCardButton(onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .height(40.dp)
            .width(150.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFC778DD), Color(0xFFFC6096))
                )
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Add My Card",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDashboardScreen() {
    MaterialTheme {
        CreateNewAccountScreen()
    }
}