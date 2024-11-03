package com.alejandro.helphub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.alejandro.helphub.features.auth.presentation.AuthViewModel
import com.alejandro.helphub.features.profile.presentation.ProfileViewModel
import com.alejandro.helphub.navigation.RootNavGraph
import com.alejandro.helphub.ui.theme.HelphubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val profileViewModel:ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController= rememberNavController()
            RootNavGraph(navController = navController,profileViewModel=profileViewModel,authViewModel=authViewModel)
            }
        }
    }


