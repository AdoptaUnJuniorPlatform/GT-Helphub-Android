package com.alejandro.helphub.presentation.auth

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.alejandro.helphub.R
import com.alejandro.helphub.presentation.navigation.RootNavGraphObjects

@Composable
fun ResetPasswordScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
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
                Spacer(modifier = Modifier.height(20.dp))
                Success()
                Spacer(modifier = Modifier.height(30.dp))
                ResetData(authViewModel)
                Spacer(modifier = Modifier.height(184.dp))
                ResetPasswordButton(authViewModel, navController)
            }
        }
    }
}

@Composable
fun ResetPasswordButton(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    val isResetEnabled by authViewModel.isPasswordResetEnabled.collectAsState()
    Button(
        onClick = {
            if (isResetEnabled) {
                Log.i("2FA", "Código 2FA correcto.")
                authViewModel.requestResetPassword()
                authViewModel.clearTwofaField()
                navController.navigate(RootNavGraphObjects.LoginScreen.route)
            }
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
}

@Composable
fun Textfield(
    value: String,
    placeholder: String,
    onTextChanged: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Email
) {
    OutlinedTextField(
        value = value, onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        placeholder = { Text(text = placeholder) },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Gray,
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray
        )
    )
}

@Composable
fun ResetData(authViewModel: AuthViewModel) {
    val inputCode by authViewModel.inputTwoFaCode.collectAsState()
    val inputNewPassword by authViewModel.inputNewPassword.collectAsState()
    val inputAgainNewPassword by authViewModel.confirmNewPassword.collectAsState()
    Text(
        text = stringResource(id = R.string.password_reset),
        color = MaterialTheme.colorScheme.primary,
        fontSize = 24.sp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = stringResource(id = R.string.reset_instructions),
        fontSize = 18.sp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Spacer(modifier = Modifier.height(40.dp))
    Textfield(
        value = inputCode,
        keyboardType = KeyboardType.Number,
        onTextChanged = {
            if (it.length <= 6) {
                authViewModel.onTwoFaCodeChanged(it)
            }
        },
        placeholder = stringResource(id = R.string.code)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Textfield(
        value = inputNewPassword,
        keyboardType = KeyboardType.Password,
        placeholder = stringResource(id = R.string.new_password),
        onTextChanged = { authViewModel.onNewPasswordChanged(it) })
    Spacer(modifier = Modifier.height(16.dp))
    Textfield(
        value = inputAgainNewPassword,
        keyboardType = KeyboardType.Password,
        placeholder = stringResource(id = R.string.confirm_new_passord),
        onTextChanged = { authViewModel.onConfirmPasswordChanged(it) })
}

@Composable
fun Success() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(90.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = Color(0x4F87DF21))
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_success_green),
                    contentDescription = stringResource(id = R.string.success),
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(id = R.string.mail_sent),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3F6810),
                    modifier = Modifier.align(
                        Alignment.CenterVertically
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text(
                    text = stringResource(id = R.string.reset_alert),
                    fontSize = 14.sp,
                    color = Color(0xFF3F6810)
                )
            }
        }
    }
}