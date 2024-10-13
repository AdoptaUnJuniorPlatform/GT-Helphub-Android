package com.alejandro.helphub.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alejandro.helphub.features.auth.presentation.AuthViewModel
import com.alejandro.helphub.features.auth.presentation.LoginScreen
import com.alejandro.helphub.features.auth.presentation.SignUpCredsScreen
import com.alejandro.helphub.features.auth.presentation.SignUpStep1
import com.alejandro.helphub.features.auth.presentation.SignUpStep2
import com.alejandro.helphub.features.auth.presentation.SignUpStep3
import com.alejandro.helphub.features.splash.presentation.SplashScreen

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "SplashScreen") {

        composable("SplashScreen") {
            SplashScreen(onNavigateToLogin = {
                navController.navigate("LoginScreen") {
                    popUpTo("SplashScreen") { inclusive = true }
                }
            })
        }
        composable("LoginScreen") {
            LoginScreen(
                authViewModel = AuthViewModel(),
                navController = navController
            )
        }
        composable("SignUpCredsScreen") {
            SignUpCredsScreen(
                authViewModel = AuthViewModel(),
                navController = navController
            )
        }
        composable("SignUpStep1"){
            SignUpStep1(
                authViewModel=AuthViewModel(),
                navController = navController)
        }
        composable("SignUpStep2"){
            SignUpStep2(authViewModel = AuthViewModel(), navController = navController)
        }
        composable("SignUpStep3"){
            SignUpStep3(authViewModel = AuthViewModel(), navController = navController)
        }

    }
}
