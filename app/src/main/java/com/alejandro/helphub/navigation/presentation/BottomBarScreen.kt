package com.alejandro.helphub.navigation.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen (
    val route:String,
    val title:String,
    val icon: ImageVector
){
    object Home: BottomBarScreen(
        route="Home",
        title = "Home",
        icon = Icons.Default.Home
    )
    object Chat: BottomBarScreen(
        route="Chat",
        title = "Chat",
        icon = Icons.Default.Mail
    )
    object Notifications: BottomBarScreen(
        route="Notifications",
        title = "Notifications",
        icon = Icons.Default.Notifications
    )
    object Profile: BottomBarScreen(
        route="Profile",
        title = "Profile",
        icon = Icons.Default.Person
    )
}