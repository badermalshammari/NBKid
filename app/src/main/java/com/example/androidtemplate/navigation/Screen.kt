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
    object ParentCardsScreen : Screen("ParentCardsScreen")
    object ChildDashboardScreen : Screen("ChildDashboardScreen")
    object StoreScreen : Screen("Store")
    object TaskScreen : Screen("TaskScreen")
    object TaskDetail : Screen("TaskDetail")
    object LeaderboardScreen : Screen("LeaderboardScreen")
    object CreateNewChildAccount : Screen("CreateNewChildAccount")
    object EnterCardScreen: Screen("EnterCardScreen")

    // parent screens
    object ParentChildScreen : Screen("ParentChildScreen")
    object AddBalanceScreen : Screen("AddBalanceScreen")
    object SettingsScreen : Screen("SettingsScreen")
    object TransferScreen : Screen("TransferScreen")

}