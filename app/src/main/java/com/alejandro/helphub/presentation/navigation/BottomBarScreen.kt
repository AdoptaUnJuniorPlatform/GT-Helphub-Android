package com.alejandro.helphub.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.alejandro.helphub.data.source.remote.server.response.UserId
import kotlinx.serialization.Serializable

@Serializable
sealed class BottomBarScreen(
    val route: String,
    val title: String,
    //val icon: ImageVector
) {

    object Home : BottomBarScreen(
        route = "Home",
        title = "Home",
        //  icon = Icons.Default.Home
    )

    object Chat : BottomBarScreen(
        route = "Chat",
        title = "Chat",
        // icon = Icons.Default.Mail
    )

    object Notifications : BottomBarScreen(
        route = "Notifications",
        title = "Notifications",
        //icon = Icons.Default.Notifications
    )

    @Serializable
    object Profile : BottomBarScreen(
        route = "Profile/{id}/{userId}",
        title = "Profile",
        // icon = Icons.Default.Person
    ) {
        fun createRoute(id: String, userId: String) = "Profile/$id/$userId"
    }

    @Serializable
    object ProfileSetupStep1 : BottomBarScreen(
        route = "ProfileSetupStep1/{email}",
        title = ""
    ) {
        fun createRoute(email: String) = "ProfileSetupStep1/$email"
    }

    @Serializable
    object ProfileSetupStep2 : BottomBarScreen(
        route = "ProfileSetupStep2/{email}",
        title = ""
    ) {
        fun createRoute(email: String) = "ProfileSetupStep2/$email"
    }

    @Serializable
    object ProfileSetupStep3 : BottomBarScreen(
        route = "ProfileSetupStep3/{email}",
        title = ""
    ) {
        fun createRoute(email: String) = "ProfileSetupStep3/$email"
    }

    @Serializable
    object ProfileSetupStep4a : BottomBarScreen(
        route = "ProfileSetupStep4a/{email}",
        title = ""
    ) {
        fun createRoute(email: String) = "ProfileSetupStep4a/$email"
    }

    @Serializable
    object ProfileSetupStep4b : BottomBarScreen(
        route = "ProfileSetupStep4b/{email}",
        title = ""
    ) {
        fun createRoute(email: String) = "ProfileSetupStep4b/$email"
    }
    @Serializable
    object ProfileSetupStep5 : BottomBarScreen(
        route = "ProfileSetupStep5",
        title = ""
    ){
        fun createRoute(email: String) = "ProfileSetupStep5/$email"
    }
    object NewSkillScreen1:BottomBarScreen(
        route = "NewSkillScreen1",
        title = ""
    )
    object NewSkillScreen2:BottomBarScreen(
        route = "NewSkillScreen2",
        title = ""
    )
    object EditProfileScreen:BottomBarScreen(
        route = "EditProfileScreen",
        title = ""
    )


}

val bottomBarIcons = mapOf(
    BottomBarScreen.Home.route to Icons.Default.Home,
    BottomBarScreen.Chat.route to Icons.Default.Mail,
    BottomBarScreen.Notifications.route to Icons.Default.Notifications,
    BottomBarScreen.Profile.route to Icons.Default.Person
)