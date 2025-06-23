package com.example.androidtemplate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import com.example.androidtemplate.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import com.example.androidtemplate.ui.composables.CreditCardDisplay
//import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.androidtemplate.ui.composables.SelectionOptionsComposable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBalanceScreen() {
    // Dummy data
    val accountName = "Khaled Account"
    val accountNumber = "12345678910"
    val cardNumber = "**** **** **** 1234"
    val validDate = "01/25"
    val cvv = "345"
    val availableBalance = "299.230 KD"
    val availableGems = "3000"
    val points = "48307"
    val gemIcon = painterResource(id = R.drawable.gems)
    val cashIcon = painterResource(id = R.drawable.transfaremoney)
    val cardLogo = painterResource(id = R.drawable.asset10)

    // State for selection
    var selectedOption by remember { mutableStateOf("Gems") }
    var amount by remember { mutableStateOf("") }
    var selectedAccount by remember { mutableStateOf("My Account") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            // Optional: Add an AppBar if needed
            TopAppBar(
                title = { Text(" Add Balance ") },
                modifier = Modifier.background(Color.White)
            )
        },
        content = { padding ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_back_arrow),
//                contentDescription = "Back",
//                tint = Color.Blue
//            )
            Text(
                text = "Add Balance",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
//            Icon(
//                painter = painterResource(id = R.drawable.ic_bell),
//                contentDescription = "Notification",
//                tint = Color.Gray
//            )
        }

        // Account Info
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = accountName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = "($accountNumber)", fontSize = 14.sp, color = Color.Gray)

        // Credit Card Section
        Spacer(modifier = Modifier.height(16.dp))
        CreditCardDisplay(
            cardNumber = cardNumber,
            validDate = validDate,
            cvv = cvv,
            logoRes = R.drawable.asset10,
            backgroundRes = R.drawable.parentcard_1_design     // get from data
        )




        // Balance Information
        Spacer(modifier = Modifier.height(16.dp))
        BalanceInfoComposable(
            availableBalance = availableBalance,
            availableGems = availableGems,
            points = points
        )

        // Selection Options
        Spacer(modifier = Modifier.height(16.dp))
        SelectionOptionsComposable(
            selectedOption = remember { mutableStateOf(selectedOption) },
            firstIcon = gemIcon,
            firstText = "Gems",
            secondIcon = cashIcon,
            secondText = "Cash"
        )

        // Amount Input
        Spacer(modifier = Modifier.height(16.dp))
        TextComposable(remember { mutableStateOf(amount) } , "Amount")


        // Footer Options
        Spacer(modifier = Modifier.height(16.dp))
//        FooterOptionsComposable(remember { mutableStateOf(selectedAccount) })

        Spacer(modifier = Modifier.height(16.dp))
        SendButton(onClick = {})
    }
        }
    )
}



@Composable
fun TextComposable(st: MutableState<String>, text: String) {
    TextField(
        value = st.value,
        onValueChange = { st.value = it },
        label = { Text(text) },
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(8.dp)
    )
}

//@Composable
//fun SelectionOptionsComposable(
//    selectedOption: MutableState<String>,
//    gemIcon: Painter,
//    cashIcon: Painter
//) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceAround
//    ) {
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//
//            Image(
//                painter = gemIcon,
//                contentDescription = "Gem Icon",
//                modifier = Modifier.size(64.dp)
//            )
//            Text(text = "Gems", fontSize = 16.sp)
//            RadioButton(
//                selected = selectedOption.value == "Gems",
//                onClick = { selectedOption.value = "Gems" }
//            )
//        }
//
//
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//
//
//                Image(
//                    painter = cashIcon,
//                    contentDescription = "Cash Icon",
//                    modifier = Modifier.size(64.dp)
//                )
//                Text(text = "Cash", fontSize = 16.sp)
//
//            RadioButton(
//                selected = selectedOption.value == "Cash",
//                onClick = { selectedOption.value = "Cash" }
//            )
//        }
//    }
//}



@Composable
fun BalanceInfoComposable(
    availableBalance: String,
    availableGems: String,
    points: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column {
            Text(text = "Available Balance", fontSize = 12.sp, color = Color.Gray)
            Text(text = availableBalance, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Column {
            Text(text = "Available Gems", fontSize = 12.sp, color = Color.Gray)
            Text(text = availableGems, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = "= 3.000 KD", fontSize = 12.sp, color = Color.Gray)
        }
        Column {
            Text(text = "Points", fontSize = 12.sp, color = Color.Gray)
            Text(text = points, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewAddBalanceScreen() {
    AddBalanceScreen()
}


