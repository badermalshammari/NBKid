package com.example.androidtemplate.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtemplate.R
import com.example.androidtemplate.ui.composables.ActionButton
import com.example.androidtemplate.ui.composables.BottomNavBar
import com.example.androidtemplate.ui.composables.CreateAccountCard
import com.example.androidtemplate.ui.composables.CreditCardDisplay
import com.example.androidtemplate.ui.composables.ZuzuAccountToggle

@Composable
fun YourCardsScreen() {
    var showZuzuAccounts by remember { mutableStateOf(true) }

    Scaffold(modifier = Modifier, bottomBar = {
        BottomNavBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding).safeContentPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardsHeader()

            Spacer(modifier = Modifier.height(16.dp))

            Text("Salary Account", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("(12345678910)", fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow (modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 8.dp))
            {
                item { CreditCardDisplay(
                    cardNumber = "**** **** **** 1234",
                    validDate = "01/25",
                    cvv = "345",
                    logoRes = R.drawable.asset10,
                    backgroundRes = R.drawable.parentcard_1_design
                ) }
                item { CreateAccountCard(
                    backgroundRes = R.drawable.emptycard
                ) }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Available Balance", fontSize = 14.sp, color = Color.Gray)
            Text("3057.656 KD", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionButton(
                    label = "Transactions",
                    icon = Icons.AutoMirrored.Filled.Send,
                    shape = RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp)
                )
                ActionButton(
                    label = "Statistics",
                    icon = Icons.Filled.BarChart,
                    shape = RoundedCornerShape(0.dp)
                )
                ActionButton(
                    label = "Settings",
                    icon = Icons.Filled.Settings,
                    shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            ZuzuAccountToggle(
                show = showZuzuAccounts,
                onToggle = { showZuzuAccounts = it }
            )

            Spacer(modifier = Modifier.height(24.dp))


        }
    }

}

@Composable
fun CardsHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(Icons.Default.Dashboard, contentDescription = "Menu")
        Text("YOUR CARDS", fontWeight = FontWeight.Bold)
        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewYourCardsScreen() {
    MaterialTheme {
        YourCardsScreen()
    }
}