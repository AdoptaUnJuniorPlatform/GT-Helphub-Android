package com.alejandro.helphub.features.auth.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun SignUpCredsScreen(loginScreenViewModel: LoginViewModel, navController: NavController){
    Box(modifier = Modifier.fillMaxWidth()){
        Text(text = "Registro") }
}