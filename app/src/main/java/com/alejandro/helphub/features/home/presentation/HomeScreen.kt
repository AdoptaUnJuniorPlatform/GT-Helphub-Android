package com.alejandro.helphub.features.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController


@Composable
fun Home(navController:NavHostController){
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "HOME")
    }
}
