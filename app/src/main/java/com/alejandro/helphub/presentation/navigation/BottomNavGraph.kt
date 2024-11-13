package com.alejandro.helphub.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alejandro.helphub.presentation.chat.ChatScreen
import com.alejandro.helphub.presentation.home.Home
import com.alejandro.helphub.presentation.notifications.NotificationsScreen
import com.alejandro.helphub.presentation.profile.ProfileScreen
import com.alejandro.helphub.presentation.profile.ProfileSetupStep1
import com.alejandro.helphub.presentation.profile.ProfileSetupStep2
import com.alejandro.helphub.presentation.profile.ProfileSetupStep3
import com.alejandro.helphub.presentation.profile.ProfileSetupStep4a
import com.alejandro.helphub.presentation.profile.ProfileSetupStep4b
import com.alejandro.helphub.presentation.profile.ProfileSetupStep5
import com.alejandro.helphub.presentation.profile.ProfileViewModel


@Composable
fun BottomNavGraph(
    navController: NavHostController,
    profileViewModel: ProfileViewModel
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(BottomBarScreen.Home.route) {
            Home(navController = navController)
        }
        composable(BottomBarScreen.Chat.route) {
            ChatScreen()
        }
        composable(BottomBarScreen.Notifications.route) {
            NotificationsScreen()
        }
        composable(
            route = BottomBarScreen.Profile.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ){navBackStackEntry->
            val id=navBackStackEntry.arguments?.getString("id")
            ProfileScreen(id=id,profileViewModel)
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
    }
}


@Composable
fun ProfileNavGraph(
    navController: NavHostController,
    profileViewModel: ProfileViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "ProfileSetupStep1"
    ) {

    }
}

