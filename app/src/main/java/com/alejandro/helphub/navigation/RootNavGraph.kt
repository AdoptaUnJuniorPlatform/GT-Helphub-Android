package com.alejandro.helphub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alejandro.helphub.features.auth.presentation.AuthViewModel
import com.alejandro.helphub.features.auth.presentation.TwofaRegisterScreen
import com.alejandro.helphub.features.auth.presentation.ForgotPasswordScreen
import com.alejandro.helphub.features.auth.presentation.LoginScreen
import com.alejandro.helphub.features.auth.presentation.MainScreen
import com.alejandro.helphub.features.auth.presentation.PrivacyScreen
import com.alejandro.helphub.features.auth.presentation.ResetPasswordScreen
import com.alejandro.helphub.features.auth.presentation.SignUpCredsScreen
import com.alejandro.helphub.features.profile.presentation.ProfileSetupStep2
import com.alejandro.helphub.features.profile.presentation.ProfileSetupStep3
import com.alejandro.helphub.features.profile.presentation.ProfileSetupStep4a
import com.alejandro.helphub.features.profile.presentation.ProfileSetupStep4b
import com.alejandro.helphub.features.profile.presentation.ProfileSetupStep5
import com.alejandro.helphub.features.auth.presentation.TwofaLoginScreen
import com.alejandro.helphub.features.home.presentation.Home
import com.alejandro.helphub.features.profile.presentation.ProfileSetupStep1
import com.alejandro.helphub.features.profile.presentation.ProfileViewModel
import com.alejandro.helphub.features.splash.presentation.SplashScreen

@Composable
fun RootNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel
) {
    NavHost(navController = navController, startDestination = "SplashScreen") {

        composable("SplashScreen") {
            SplashScreen(onNavigateToLogin = {
                navController.navigate("MainScreen") {
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
        composable("ProfileSetupStep1") {
            ProfileSetupStep1(
                profileViewModel = profileViewModel,
                navController = navController
            )
        }
        composable("ProfileSetupStep2") {
            ProfileSetupStep2(
                profileViewModel = profileViewModel,
                navController = navController
            )
        }
        composable("ProfileSetupStep3") {
            ProfileSetupStep3(
                profileViewModel = profileViewModel,
                navController = navController
            )
        }
        composable("ProfileSetupStep4a") {
            ProfileSetupStep4a(
                profileViewModel = profileViewModel,
                navController = navController
            )
        }
        composable("ProfileSetupStep4b") {
            ProfileSetupStep4b(
                profileViewModel = profileViewModel,
                navController = navController
            )
        }
        composable("ProfileSetupStep5") {
            ProfileSetupStep5(
                profileViewModel = profileViewModel,
                navController = navController
            )
        }
        composable("MainScreen") {
            MainScreen(navController = navController)
        }
        composable("Home") {
            Home(navController = navController)
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
}