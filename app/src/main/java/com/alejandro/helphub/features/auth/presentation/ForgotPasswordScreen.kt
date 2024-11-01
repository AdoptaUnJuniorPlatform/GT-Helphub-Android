package com.alejandro.helphub.features.auth.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.alejandro.helphub.R
import com.alejandro.helphub.utils.ResultStatus

@Composable
fun ForgotPasswordScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel

) {
    val userData by authViewModel.userData.collectAsState()
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = 16.dp
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(0f)
            ) {
                Logo()
                Spacer(modifier = Modifier.height(24.dp))
                ResetPassword()
                Spacer(modifier = Modifier.height(20.dp))
                Email(
                    email = userData.email,
                    colorContainer = Color.Transparent,
                    onTextChanged = { authViewModel.updateUserEmail(it) })
                Spacer(modifier = Modifier.height(410.dp))
                ResetButton(authViewModel, navController)
            }
        }
    }
}

@Composable
fun ResetButton(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    val twoFaStatus by authViewModel.twoFaStatus.collectAsState()
    val context = LocalContext.current
    Button(
        onClick = {
            authViewModel.generateAndSendTwoFaCodeResetPassword()
            navController.navigate("ResetPasswordScreen")
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = stringResource(id = R.string.reset_password).uppercase()
        )
    }
    when (twoFaStatus) {
        is ResultStatus.Success -> {
            authViewModel.resetTwoFaStatus()
            navController.navigate("ResetPasswordScreen")
        }

        is ResultStatus.Error -> {
            authViewModel.resetTwoFaStatus()
            Toast.makeText(
                context,
                (twoFaStatus as ResultStatus.Error).message,
                Toast.LENGTH_SHORT
            ).show()
            navController.navigate("LoginScreen")
        }

        else -> {} // No hacer nada en el estado Idle
    }
}

@Composable
fun ResetPassword() {
    Text(
        text = stringResource(id = R.string.password_reset),
        color = MaterialTheme.colorScheme.primary,
        fontSize = 24.sp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = stringResource(id = R.string.reset_text),
        fontSize = 18.sp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}