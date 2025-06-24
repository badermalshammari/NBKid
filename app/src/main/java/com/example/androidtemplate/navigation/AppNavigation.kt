package com.example.androidtemplate.navigation

import GiftsScreen
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidtemplate.navigation.Screen.*
import com.example.androidtemplate.ui.screens.*
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    nbkidsViewModel: NBKidsViewModel,
    cardScreenViewModel: CardScreenViewModel,
    walletViewModel: WalletViewModel
) {
    NavHost(navController = navController, startDestination = Login.route) {

        composable(Login.route) {
            LoginScreen(viewModel = nbkidsViewModel, navController = navController)
        }

        composable(Home.route) {
            HomeScreen(viewModel = nbkidsViewModel, navController = navController)
        }

        composable(Register.route) {
            RegisterScreen(viewModel = nbkidsViewModel, navController = navController)
        }

        composable(SignupSuccess.route) {
            SignupSuccessScreen(navController = navController)
        }

        composable(SignupFailureScreen.route) {
            SignupFailureScreen(navController = navController)
        }

        composable(PolicyScreen.route) {
            PolicyScreen(navController = navController)
        }

        composable(SelectKidScreen.route) {
            SelectKidScreen(viewModel = nbkidsViewModel, navController = navController)
        }

        composable(ParentCardsScreen.route) {
            ParentCardsScreen(
                mainViewModel = nbkidsViewModel,
                cardViewModel = cardScreenViewModel,
                navController = navController,
                walletViewModel = walletViewModel,
            )
        }

        composable(ChildDashboardScreen.route) {
            ChildDashboardScreen(
                nbkidsViewModel = nbkidsViewModel,
                navController = navController
            )
        }

        composable(StoreScreen.route) {
            StoreScreen(
                nbkidsViewModel = nbkidsViewModel,
                navController = navController
            )
        }
        composable("enter_card_screen/{cardId}") { backStackEntry ->
            val cardId = backStackEntry.arguments?.getString("cardId")?.toLongOrNull()
            if (cardId != null) {
                GiftsScreen(
                    cardId = cardId,
                    cardViewModel = cardScreenViewModel,
                    walletViewModel = walletViewModel,
                    navController = navController
                )
            }
        }
        composable(TaskScreen.route) {
            TaskScreen(
                nbkidsViewModel = nbkidsViewModel,
                navController = navController
            )
        }


        composable("taskDetail") {
            val task = nbkidsViewModel.selectedTask
            if (task != null) {
                TaskDetail(
                    task = task,
                    onBackClick = { navController.popBackStack() }
                )
            } else {
                Text("No task selected", color = Color.Red)
            }
        }

        composable(LeaderboardScreen.route) {
            LeaderboardScreen(
                nbkidsViewModel = nbkidsViewModel,
                navController = navController
            )
        }

        composable(CreateNewChildAccount.route) {
            CreateNewChildAccount(
                mainViewModel = nbkidsViewModel,
                cardViewModel = cardScreenViewModel,
                navController = navController
            )
        }

        composable("enter_card_screen/{cardId}") { backStackEntry ->
            val cardId = backStackEntry.arguments?.getString("cardId")?.toLongOrNull()
            if (cardId != null) {
                EnterCardScreen(
                    cardId = cardId,
                    cardViewModel = cardScreenViewModel,
                    walletViewModel = walletViewModel,
                    navController = navController
                )
            }
        }

    }
}