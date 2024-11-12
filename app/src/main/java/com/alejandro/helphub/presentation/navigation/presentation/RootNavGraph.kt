package com.alejandro.helphub.presentation.navigation.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alejandro.helphub.MainScreen
import com.alejandro.helphub.presentation.auth.AuthViewModel
import com.alejandro.helphub.presentation.auth.TwofaRegisterScreen
import com.alejandro.helphub.presentation.auth.ForgotPasswordScreen
import com.alejandro.helphub.presentation.auth.LoginScreen
import com.alejandro.helphub.presentation.auth.AuthScreen
import com.alejandro.helphub.presentation.auth.PrivacyScreen
import com.alejandro.helphub.presentation.auth.ResetPasswordScreen
import com.alejandro.helphub.presentation.auth.SignUpCredsScreen
import com.alejandro.helphub.presentation.auth.TwofaLoginScreen
import com.alejandro.helphub.presentation.home.Home
import com.alejandro.helphub.presentation.profile.ProfileViewModel
import com.alejandro.helphub.presentation.splash.SplashScreen

@Composable
fun RootNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel,
    navigationViewModel: NavigationViewModel
) {
    NavHost(navController = navController, startDestination = "SplashScreen") {

        authNavGraph(navController, authViewModel)

        composable("MainScreen") {
            MainScreen(
                navigationViewModel = navigationViewModel,
                navController = navController,
                profileViewModel=profileViewModel
            )
        }
        composable("Home") {
            Home(navController = navController)
        }

    }
}

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    composable("SplashScreen") {
        SplashScreen(onNavigateToLogin = {
            navController.navigate("AuthScreen") {
                popUpTo("SplashScreen") { inclusive = true }
            }
        })
    }
    composable("LoginScreen") {
        LoginScreen(
            authViewModel = authViewModel,
            navController = navController
        )
    }
    composable("SignUpCredsScreen") {
        SignUpCredsScreen(
            authViewModel = authViewModel,
            navController = navController
        )
    }
    composable("PrivacyScreen") {
        PrivacyScreen(
            navController = navController
        )
    }
    composable("AuthScreen") {
        AuthScreen(navController = navController)
    }
    composable("ForgotPasswordScreen") {
        ForgotPasswordScreen(
            authViewModel = authViewModel,
            navController = navController
        )
    }
    composable("ResetPasswordScreen") {
        ResetPasswordScreen(
            authViewModel = authViewModel,
            navController = navController
        )
    }
    composable("TwofaRegisterScreen") {
        TwofaRegisterScreen(
            authViewModel = authViewModel,
            navController = navController
        )
    }
    composable("TwofaLoginScreen") {
        TwofaLoginScreen(
            authViewModel = authViewModel,
            navController = navController
        )
    }
}

