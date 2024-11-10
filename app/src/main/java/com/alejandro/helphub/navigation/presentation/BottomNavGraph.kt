package com.alejandro.helphub.navigation.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alejandro.helphub.features.chat.presentation.ChatScreen
import com.alejandro.helphub.features.home.presentation.Home
import com.alejandro.helphub.features.notifications.presentation.NotificationsScreen
import com.alejandro.helphub.features.profile.presentation.ProfileScreen
import com.alejandro.helphub.features.profile.presentation.ProfileSetupStep1
import com.alejandro.helphub.features.profile.presentation.ProfileSetupStep2
import com.alejandro.helphub.features.profile.presentation.ProfileSetupStep3
import com.alejandro.helphub.features.profile.presentation.ProfileSetupStep4a
import com.alejandro.helphub.features.profile.presentation.ProfileSetupStep4b
import com.alejandro.helphub.features.profile.presentation.ProfileSetupStep5
import com.alejandro.helphub.features.profile.presentation.ProfileViewModel


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
        composable(BottomBarScreen.Profile.route) {
            ProfileScreen()
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

