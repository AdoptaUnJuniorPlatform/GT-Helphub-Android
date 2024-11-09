package com.alejandro.helphub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.alejandro.helphub.features.auth.presentation.AuthViewModel
import com.alejandro.helphub.features.profile.presentation.ProfileViewModel
import com.alejandro.helphub.navigation.presentation.BottomNavGraph
import com.alejandro.helphub.navigation.presentation.NavigationViewModel
import com.alejandro.helphub.navigation.presentation.RootNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val navigationViewModel:NavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val isAuthenticated =
                authViewModel.isAuthenticated.collectAsState().value
            if (isAuthenticated) {
                MainScreen(navigationViewModel = navigationViewModel)
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


