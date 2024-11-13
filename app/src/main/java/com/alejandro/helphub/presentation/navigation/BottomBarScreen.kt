package com.alejandro.helphub.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable
@Serializable
sealed class BottomBarScreen (
    val route:String,
    val title:String,
    //val icon: ImageVector
){

    object Home: BottomBarScreen(
        route="Home",
        title = "Home",
      //  icon = Icons.Default.Home
    )
    object Chat: BottomBarScreen(
        route="Chat",
        title = "Chat",
       // icon = Icons.Default.Mail
    )
    object Notifications: BottomBarScreen(
        route="Notifications",
        title = "Notifications",
        //icon = Icons.Default.Notifications
    )
    @Serializable
    object Profile: BottomBarScreen(
        route="Profile/{id}",
        title = "Profile",
       // icon = Icons.Default.Person
    ){fun createRoute(id:String)="Profile/$id"}
}
val bottomBarIcons = mapOf(
    BottomBarScreen.Home.route to Icons.Default.Home,
    BottomBarScreen.Chat.route to Icons.Default.Mail,
    BottomBarScreen.Notifications.route to Icons.Default.Notifications,
    BottomBarScreen.Profile.route to Icons.Default.Person
)