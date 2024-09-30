package com.alejandro.helphub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alejandro.helphub.features.auth.presentation.LoginScreen
import com.alejandro.helphub.features.splash.presentation.SplashScreen

@Composable
fun RootNavGraph(navController:NavHostController){
    NavHost(navController = navController, startDestination = "SplashScreen") {

        composable("SplashScreen"){
            SplashScreen (onNavigateToLogin = {
                navController.navigate("LoginScreen"){
                    popUpTo("SplashScreen"){inclusive=true}
                }
            })
        }
        composable("LoginScreen"){
            LoginScreen()
        }
    }
}