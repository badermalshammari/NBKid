package com.example.androidtemplate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidtemplate.data.requests.RegisterRequest
import com.example.androidtemplate.navigation.Screen.PolicyScreen
import com.example.androidtemplate.navigation.Screen.SignupFailureScreen
import com.example.androidtemplate.ui.screens.HomeScreen
import com.example.androidtemplate.ui.screens.LoginScreen
import com.example.androidtemplate.ui.screens.PolicyScreen
import com.example.androidtemplate.ui.screens.RegisterScreen
import com.example.androidtemplate.ui.screens.SignupFailureScreen
import com.example.androidtemplate.ui.screens.SignupSuccessScreen
import com.example.androidtemplate.viewmodels.NBKidsViewModel

@Composable
fun AppNavigation(navController: NavHostController, nbkidsViewModel: NBKidsViewModel) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(viewModel = nbkidsViewModel, navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(viewModel = nbkidsViewModel, navController = navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(viewModel = nbkidsViewModel, navController = navController)
        }
        composable(Screen.SignupSuccess.route) {
            SignupSuccessScreen(navController = navController)
        }
        composable(Screen.SignupFailureScreen.route) {
            SignupFailureScreen(navController = navController)
        }
        composable(PolicyScreen.route){
            PolicyScreen(navController = navController)
        }
    }
}