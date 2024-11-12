package com.alejandro.helphub.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.alejandro.helphub.R
import com.alejandro.helphub.utils.ResultStatus

@Composable
fun LoginScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val userData by authViewModel.userAuthData.collectAsState()
    val isChecked: Boolean by authViewModel.isCheckBoxChecked.collectAsState(
        initial = false
    )
    LaunchedEffect(Unit) {
        authViewModel.clearTwofaField()
        authViewModel.clearPasswordsField()
    }
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
                LoginTitle()
                EmailLabel()
                Email(
                    userData.email,
                    colorContainer = MaterialTheme.colorScheme.primaryContainer,
                    onTextChanged = { authViewModel.updateUserEmail(it) }
                )
                Spacer(modifier = Modifier.height(4.dp))
                PasswordLabel(onClick = { navController.navigate("ForgotPasswordScreen") })
                Spacer(modifier = Modifier.height(10.dp))
                Password(userData.password) {
                    authViewModel.updateUserPassword(
                        it
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Remember(
                    isChecked = isChecked,
                    authViewModel = authViewModel
                ) //cambiar con Flow
                Spacer(modifier = Modifier.height(300.dp))
                LoginButton(
                    authViewModel, navController = navController,
                    text = stringResource(id = R.string.login)
                )
                Spacer(modifier = Modifier.height(12.dp))
                SignUpLink(onClick = { navController.navigate("SignUpCredsScreen") })
            }
        }
    }
}

@Composable
fun SignUpLink(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.register_question))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = stringResource(id = R.string.signup), color = Color.Blue,
            modifier = Modifier.clickable { onClick() })
    }
}

@Composable
fun LoginButton(
    authViewModel: AuthViewModel,
    text: String,
    navController: NavHostController
) {
    val twoFaStatus by authViewModel.twoFaStatus.collectAsState()
    val context = LocalContext.current
    Button(
        onClick = {
            authViewModel.generateAndSendTwoFaCodeLogin()
            navController.navigate("TwofaLoginScreen")
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
            text = text.uppercase()
        )
    }
    when (twoFaStatus) {
        is ResultStatus.Success -> {
            authViewModel.resetTwoFaStatus()
            navController.navigate("TwofaLoginScreen")
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
fun Remember(isChecked: Boolean, authViewModel: AuthViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Checkbox(checked = isChecked, onCheckedChange = { newValue ->
            authViewModel.onCheckBoxCheckedChanged(newValue)
        })
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(id = R.string.remember_me),
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun Password(password: String, onTextChanged: (String) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        placeholder = { Text(text = stringResource(id = R.string.password_example)) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Gray,
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray,
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray
        ),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisibility) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(
                    imageVector = image,
                    contentDescription = stringResource(id = R.string.ic_visibility_content_description)
                )
            }
        },
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
}

@Composable
fun PasswordLabel(onClick: () -> Unit) {
    Row {
        Text(
            text = stringResource(id = R.string.password),
            fontSize = 16.sp,
            modifier = Modifier
                .padding(start = 16.dp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(60.dp))
        Text(
            text = stringResource(id = R.string.forgot_password),
            fontSize = 16.sp,
            color = Color.Blue,
            modifier = Modifier.clickable { onClick() }
        )
    }
}

@Composable
fun Email(
    email: String,
    onTextChanged: (String) -> Unit,
    colorContainer: Color
) {
    OutlinedTextField(
        value = email, onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        placeholder = { Text(text = stringResource(id = R.string.email_example)) },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Gray,
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray,
            focusedContainerColor = colorContainer,
            unfocusedContainerColor = colorContainer,
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray
        )
    )
}

@Composable
fun EmailLabel() {
    Text(
        text = stringResource(id = R.string.email),
        fontSize = 16.sp,
        modifier = Modifier
            .padding(start = 16.dp)
            .offset(y = 5.dp),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun LoginTitle() {
    Text(
        text = stringResource(id = R.string.login),
        color = MaterialTheme.colorScheme.primary,
        fontSize = 26.sp,
        modifier = Modifier.padding(16.dp)
    )
}