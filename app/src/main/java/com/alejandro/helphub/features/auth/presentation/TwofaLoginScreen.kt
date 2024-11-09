package com.alejandro.helphub.features.auth.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.alejandro.helphub.features.profile.presentation.RegisterHeader
import com.alejandro.helphub.features.profile.presentation.StepButtons
import com.alejandro.helphub.utils.ResultStatus

@Composable
fun TwofaLoginScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    var buttonsEnabled by remember { mutableStateOf(true) }
    var showSuccessCard by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val loginStatus by authViewModel.loginStatus.collectAsState()
    LaunchedEffect(loginStatus) {
        when (loginStatus) {
            is ResultStatus.Success -> showSuccessCard = true
            is ResultStatus.Error -> {
                Toast.makeText(
                    context,
                    "Error durante el login",
                    Toast.LENGTH_SHORT
                ).show()
                navController.navigate("LoginScreen") {
                    popUpTo("TwofaLoginScreen") { inclusive = true }
                }
            }
            else -> {} // Manejo de otros estados si es necesario
        }
    }
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(0f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    RegisterHeader()
                    Spacer(modifier = Modifier.height(30.dp))
                    AuthCode(authViewModel)
                    Spacer(modifier = Modifier.height(30.dp))
                    Retry(authViewModel)
                    Spacer(modifier = Modifier.height(180.dp))
                    VerificationMessage()
                }
                LoginValidationButton(
                    navController,
                    authViewModel,
                    buttonsEnabled
                )
            }
        }
    }
    if (showSuccessCard) {
        SuccessCard(onNavigate = { navController.navigate("MainScreen") })
    }
}

@Composable
fun LoginValidationButton(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    enabled: Boolean,
) {
    val isTwoFaValid = remember { mutableStateOf(false) }
    StepButtons(
        onBackClick = {
            if (enabled) {
                navController.popBackStack()
            }
        },
        onNextClick = {
            isTwoFaValid.value = authViewModel.isTwoFaCodeValid()
            if (isTwoFaValid.value) {
                Log.i("2FA", "Código 2FA correcto.")
                authViewModel.loginUser()
                authViewModel.clearTwofaField()
                navController.navigate("MainScreen"){popUpTo(0)
                launchSingleTop=true}
            } else {
                Log.i("2FA", "Código 2FA incorrecto.")
            }
        },
        enabled = enabled
    )
}