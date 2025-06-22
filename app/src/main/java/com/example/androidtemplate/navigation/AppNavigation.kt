package com.example.androidtemplate.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidtemplate.navigation.Screen.*
import com.example.androidtemplate.ui.screens.*
import com.example.androidtemplate.viewmodels.CardScreenViewModel
import com.example.androidtemplate.viewmodels.NBKidsViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    nbkidsViewModel: NBKidsViewModel
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
            val context = LocalContext.current
            val cardViewModel = remember { CardScreenViewModel(context) }

            ParentCardsScreen(
                mainViewModel = nbkidsViewModel,
                cardViewModel = cardViewModel,
                navController = navController
            )
        }
        composable(ChildDashboardScreen.route) {
            ChildDashboardScreen(nbkidsViewModel = nbkidsViewModel)
        }
    }
}