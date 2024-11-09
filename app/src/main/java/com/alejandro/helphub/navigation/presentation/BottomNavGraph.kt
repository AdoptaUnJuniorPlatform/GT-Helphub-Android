package com.alejandro.helphub.navigation.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alejandro.helphub.features.chat.presentation.ChatScreen
import com.alejandro.helphub.features.home.presentation.Home
import com.alejandro.helphub.features.notifications.presentation.NotificationsScreen
import com.alejandro.helphub.features.profile.presentation.ProfileScreen


@Composable
fun BottomNavGraph(navController: NavHostController){
    NavHost(
        navController=navController,
        startDestination= BottomBarScreen.Home.route
    ){
        composable(BottomBarScreen.Home.route){
            Home(navController = navController)
        }
        composable(BottomBarScreen.Chat.route){
            ChatScreen()
        }
        composable(BottomBarScreen.Notifications.route){
            NotificationsScreen()
        }
        composable(BottomBarScreen.Profile.route){
            ProfileScreen()
        }
    }
}

