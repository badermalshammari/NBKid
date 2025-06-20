package com.example.androidtemplate.utils

import androidx.navigation.NavController
import com.example.androidtemplate.navigation.Screen
import com.example.androidtemplate.viewmodels.NBKidsViewModel

fun Logout(viewModel: NBKidsViewModel, navController: NavController) {
    viewModel.logout()
    navController.navigate(Screen.Login.route) {
        popUpTo(0) { inclusive = true }
    }
}