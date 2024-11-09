package com.alejandro.helphub

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alejandro.helphub.navigation.presentation.BottomBarScreen
import com.alejandro.helphub.navigation.presentation.BottomNavGraph
import com.alejandro.helphub.navigation.presentation.NavigationViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen (navigationViewModel: NavigationViewModel) {
    val navController = rememberNavController()
    Scaffold(bottomBar = {
        BottomBar(
            navController = navController, navigationViewModel
        )
    }) { paddingValues ->
        Box(modifier = Modifier.padding(PaddingValues())) {
            BottomNavGraph(
                navController = navController
            )
        }
    }
}


@Composable
fun BottomBar(
    navController: NavController, navigationViewModel: NavigationViewModel
) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Chat,
        BottomBarScreen.Notifications,
        BottomBarScreen.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val profileStatusCode =
        navigationViewModel.profileStatusCode.collectAsState().value
    var showPopUp by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 30.dp)
            .background(
                MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { screen ->
            NavigationBarItem(label = {
                if (currentDestination?.route == screen.route) Text(
                    text = screen.title, fontSize = 12.sp
                )
            },
                selected = currentDestination?.route == screen.route,
                onClick = {
                    if (screen.route == BottomBarScreen.Profile.route) {
                        navigationViewModel.fetchUserProfile()


                        if (profileStatusCode == 200) {
                            navController.navigate(screen.route)
                        } else {
                            showPopUp = true
                        }
                    } else {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title,
                        modifier = Modifier.offset(y = 8.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedIconColor = Color.LightGray,
                    unselectedTextColor = Color.White
                )
            )
        }
    }
    if (showPopUp) {
        ShowPopUp(OnDismiss = { showPopUp = false })
    }
}

@Composable
fun ShowPopUp(OnDismiss: () -> Unit) {
    AlertDialog(onDismissRequest = { OnDismiss() }, confirmButton = { })
}
