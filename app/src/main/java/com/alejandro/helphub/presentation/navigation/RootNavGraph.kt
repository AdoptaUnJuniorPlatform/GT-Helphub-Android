package com.alejandro.helphub.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alejandro.helphub.MainScreen
import com.alejandro.helphub.presentation.auth.AuthViewModel
import com.alejandro.helphub.presentation.auth.TwofaRegisterScreen
import com.alejandro.helphub.presentation.auth.LoginScreen
import com.alejandro.helphub.presentation.auth.AuthScreen
import com.alejandro.helphub.presentation.auth.ForgotPasswordScreen
import com.alejandro.helphub.presentation.auth.PrivacyScreen
import com.alejandro.helphub.presentation.auth.ResetPasswordScreen
import com.alejandro.helphub.presentation.auth.SignUpCredsScreen
import com.alejandro.helphub.presentation.auth.TwofaLoginScreen
import com.alejandro.helphub.presentation.home.Home
import com.alejandro.helphub.presentation.profile.ProfileViewModel
import com.alejandro.helphub.presentation.splash.SplashScreen
@Composable
fun RootNavGraph(navController: NavHostController,
                authViewModel: AuthViewModel,
                profileViewModel: ProfileViewModel,
                navigationViewModel: NavigationViewModel
)
{
    NavHost(navController = navController, startDestination = "SplashScreen") {
        composable(RootNavGraphObjects.MainScreen.route,
            arguments= listOf(navArgument("email"){type=NavType.StringType})
        ) {navBackStackEntry->
            val email= navBackStackEntry.arguments?.getString("email")
            MainScreen(
                navigationViewModel = navigationViewModel,
                navController = navController,
                profileViewModel = profileViewModel,
                email=email
            )
        }
        composable(RootNavGraphObjects.Home.route) {
            Home(navController = navController)
        }
        composable(RootNavGraphObjects.SplashScreen.route) {
            SplashScreen(onNavigateToLogin = {
                navController.navigate("AuthScreen") {
                    popUpTo("SplashScreen") { inclusive = true }
                }
            })
        }
        composable(RootNavGraphObjects.LoginScreen.route) {
            LoginScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }
        composable(RootNavGraphObjects.SignUpCredsScreen.route) {
            SignUpCredsScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }
        composable(RootNavGraphObjects.PrivacyScreen.route) {
            PrivacyScreen(
                navController = navController
            )
        }
        composable(RootNavGraphObjects.AuthScreen.route) {
            AuthScreen(navController = navController)
        }
        composable(RootNavGraphObjects.ForgotPasswordScreen.route) {
            ForgotPasswordScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }
        composable(RootNavGraphObjects.TwofaRegisterScreen.route) {
            TwofaRegisterScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }
        composable(RootNavGraphObjects.TwofaLoginScreen.route) {
            TwofaLoginScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }
        composable(RootNavGraphObjects.ResetPasswordScreen.route) {
            ResetPasswordScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }


    }

}

