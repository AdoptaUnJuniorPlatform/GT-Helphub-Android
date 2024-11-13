package com.alejandro.helphub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import com.alejandro.helphub.presentation.auth.AuthViewModel
import com.alejandro.helphub.presentation.profile.ProfileViewModel
import com.alejandro.helphub.presentation.navigation.BottomNavGraph
import com.alejandro.helphub.presentation.navigation.NavigationViewModel
import com.alejandro.helphub.presentation.navigation.RootNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val navigationViewModel: NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val isAuthenticated =
                authViewModel.isAuthenticated.collectAsState().value
            if (isAuthenticated) {
                BottomNavGraph(navController = navController, profileViewModel = profileViewModel)
               MainScreen(navigationViewModel = navigationViewModel,navController=navController,profileViewModel)
            }else{
                RootNavGraph(
                    navController = navController,
                    profileViewModel = profileViewModel,
                    authViewModel = authViewModel,
                    navigationViewModel=navigationViewModel
                )
            }
        }
    }
}


