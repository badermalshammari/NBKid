//package com.example.androidtemplate.ui.screens
//
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.foundation.clickable
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Notifications
//import androidx.compose.material.icons.filled.Person
//import com.example.androidtemplate.ui.composables.SettingsNavigationHeader
//
//@Composable
//fun SettingsScreen(
//    modifier: Modifier = Modifier,
//    onBackClick: () -> Unit = {},
//    onChangePictureClick: () -> Unit = {},
//    onDisableAccountClick: () -> Unit = {},
//    onBackToHomeClick: () -> Unit = {}
//) {
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .background(Color.White)
//            .padding(horizontal = 20.dp)
//            .padding(top = 40.dp)
//    ) {
//        // Navigation Header
//        SettingsNavigationHeader(onBackClick = onBackClick)
//
//        //Spacer(modifier = Modifier.height(32.dp))
//
//        // Account Info
//        AccountInformation()
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Credit Card Section
//        PaymentCard()
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Balance
//        BalanceInformation()
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Actions
//        ProfileActionSection(
//            onChangePictureClick = onChangePictureClick,
//            onDisableAccountClick = onDisableAccountClick,
//            onBackToHomeClick = onBackToHomeClick
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//    }
//}
//
//
//
//
//@Composable
//fun ProfileActionSection(
//    onChangePictureClick: () -> Unit,
//    onDisableAccountClick: () -> Unit,
//    onBackToHomeClick: () -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Profile Avatar (Left)
//            Box(
//                modifier = Modifier
//                    .size(120.dp)
//                    .clip(CircleShape)
//                    .background(Color(0xFFE8F4FD)),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Person,
//                    contentDescription = "Profile",
//                    tint = Color(0xFF4A90E2),
//                    modifier = Modifier.size(72.dp)
//                )
//            }
//
//            Spacer(modifier = Modifier.width(16.dp))
//
//            // Disable + Change Picture (Right)
//            Column(
//                modifier = Modifier.weight(1f),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                ActionButton(
//                    label = "Disable Account",
//                    onClick = onDisableAccountClick,
//                    backgroundColor = Color(0xFFEF4444),
//                    contentColor = Color.White
//                )
//                Spacer(modifier = Modifier.height(12.dp))
//                ActionButton(
//                    label = "Change Picture",
//                    onClick = onChangePictureClick,
//                    backgroundColor = Color(0xFF4A90E2),
//                    contentColor = Color.White
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Centered Back to Home Button
//        ActionButton(
//            label = "Back To Home",
//            onClick = onBackToHomeClick,
//            backgroundColor = Color(0xFFF3F4F6),
//            contentColor = Color.Black
//        )
//    }
//}
//
//
//@Composable
//fun ActionButton(
//    label: String,
//    onClick: () -> Unit,
//    backgroundColor: Color,
//    contentColor: Color
//) {
//    Button(
//        onClick = onClick,
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(52.dp),
//        colors = ButtonDefaults.buttonColors(
//            containerColor = backgroundColor,
//            contentColor = contentColor
//        ),
//        shape = RoundedCornerShape(24.dp)
//    ) {
//        Text(
//            text = label,
//            style = MaterialTheme.typography.titleMedium,
//            fontWeight = FontWeight.Bold
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun SettingsScreenPreview() {
//    MaterialTheme {
//        SettingsScreen()
//    }
//}