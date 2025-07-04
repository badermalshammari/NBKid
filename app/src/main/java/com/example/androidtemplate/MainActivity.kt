package com.example.androidtemplate

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.androidtemplate.navigation.AppNavigation
import com.example.androidtemplate.ui.theme.AndroidTemplateTheme
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.LeaderboardViewModel
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.TaskViewModel
import com.example.androidtemplate.viewmodels.TransferViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidTemplateTheme {
                val navController = rememberNavController()
                val context = this

                val viewModel = NBKidsViewModel(context)
                val cardScreenViewModel = CardScreenViewModel(context)
                val walletViewModel = WalletViewModel(context)
                val leaderboardViewModel = LeaderboardViewModel(context)


                val transferViewModel = TransferViewModel(context)

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation(
                            navController = navController,
                            nbkidsViewModel = viewModel,
                            cardScreenViewModel = cardScreenViewModel,
                            walletViewModel = walletViewModel,
                            taskViewModel = TaskViewModel(context),
                            leaderboardViewModel = leaderboardViewModel,
                            transferViewModel = transferViewModel
                        )
                    }
                }
            }
        }
    }
}