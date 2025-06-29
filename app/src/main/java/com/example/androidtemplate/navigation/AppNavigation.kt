package com.example.androidtemplate.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.androidtemplate.navigation.Screen.*
import com.example.androidtemplate.ui.screens.*
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.LeaderboardViewModel
import com.example.androidtemplate.viewmodels.NBKidsViewModel
import com.example.androidtemplate.viewmodels.TaskViewModel
import com.example.androidtemplate.viewmodels.WalletViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    nbkidsViewModel: NBKidsViewModel,
    cardScreenViewModel: CardScreenViewModel,
    walletViewModel: WalletViewModel,
    taskViewModel: TaskViewModel,
    leaderboardViewModel: LeaderboardViewModel
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
        composable(TaskScreen.route) {
            TaskScreen(
                nbkidsViewModel = nbkidsViewModel,
                navController = navController
            )
        }

        composable(
            route = "LeaderboardParent/{cardId}",
            arguments = listOf(navArgument("cardId") { type = NavType.LongType })
        ) { backStackEntry ->
            val cardId = backStackEntry.arguments?.getLong("cardId") ?: return@composable

            LeaderboardParent(
                cardId = cardId,
                cardViewModel = cardScreenViewModel,
                leaderboardViewModel = leaderboardViewModel,
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

        composable(CreateNewParentAccount.route) {
            CreateNewParentAccount(
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
        composable("add_balance_screen/{childId}") { backStackEntry ->
            val childId = backStackEntry.arguments?.getString("childId")?.toLongOrNull() ?: return@composable
            AddBalanceScreen(
                navController = navController,
                walletViewModel = walletViewModel,
                childId = childId
            )
        }
        composable("add_task_screen/{cardId}") { backStackEntry ->
            val cardId = backStackEntry.arguments?.getString("cardId")?.toLongOrNull() ?: return@composable
            AddTaskScreen(
                cardId = cardId,
                cardViewModel = cardScreenViewModel,
                walletViewModel = walletViewModel,
                navController = navController,
                taskViewModel = taskViewModel,
                nbKidsViewModel = nbkidsViewModel
            )
        }
        composable("gifts_screen/{cardId}") { backStackEntry ->
            val cardId = backStackEntry.arguments?.getString("cardId")?.toLongOrNull()
            if (cardId != null) {
                GiftsScreen(
                    cardId = cardId,
                    cardViewModel = cardScreenViewModel,
                    walletViewModel = walletViewModel,
                    nbkidsViewModel = nbkidsViewModel,
                    navController = navController
                )
            } else {
                Text("Invalid Card ID", color = Color.Red)
            }
        }

    }

}