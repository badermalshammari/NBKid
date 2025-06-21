package com.example.androidtemplate.navigation

sealed class Screen(val route: String){
    // Auth Screens
    object Login : Screen("login")
    object Register : Screen("register")
    object SignupSuccess : Screen("signupsuccess")
    object SignupFailureScreen : Screen("signupfailurescreen")
    object PolicyScreen : Screen("policy")
    object SelectKidScreen: Screen("selectkid")

    // User Screens
    object Home : Screen("home")
}
