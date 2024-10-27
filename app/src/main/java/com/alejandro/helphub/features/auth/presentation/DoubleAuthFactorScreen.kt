package com.alejandro.helphub.features.auth.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.alejandro.helphub.R


@Composable
fun DoubleAuthFactorScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    var showSuccessCard by remember { mutableStateOf(false) }
    var buttonsEnabled by remember { mutableStateOf(true) }
    val context = LocalContext.current
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
                RegisterValidationButton(
                    navController,
                    authViewModel,
                    buttonsEnabled
                ) { result ->
                    if (result) {
                        showSuccessCard = true
                    } else {
                        Toast.makeText(
                            context,
                            "Error durante el registro",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.navigate("LoginScreen") {
                            popUpTo("DoubleAuthFactorScreen") {
                                inclusive = true
                            }
                        }
                    }
                    buttonsEnabled = false
                }
            }
        }
    }
    if (showSuccessCard) {
        SuccessCard(navController)
    }
}

@Composable
fun RegisterValidationButton(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    enabled: Boolean,
    onSuccess: (Boolean) -> Unit
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
                authViewModel.registerUser() { result ->
                    if (result) {
                        onSuccess(true)
                    } else {
                        onSuccess(false)
                    }
                }
            } else {
                Log.i("2FA", "Código 2FA incorrecto.")
                onSuccess(false)
            }
        },
        enabled = enabled
    )
}

@Composable
fun SuccessCard(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 24.dp)
                .offset(y = (-20).dp)
                .border(
                    width = 2.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(
                        12.dp
                    )
                ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(horizontal = 15.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 12.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(imageVector = Icons.Default.Close,
                        contentDescription = stringResource(
                            id = R.string.close
                        ),
                        modifier = Modifier.clickable { navController.navigate("LoginScreen") })
                }
                Spacer(modifier = Modifier.height(36.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_success_green),
                    contentDescription = stringResource(id = R.string.success),
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.congratulations),
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.verification_success),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .wrapContentHeight()
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(
                            Color(0xFFFBFBFF)
                        )
                ) {
                    Text(
                        text = stringResource(id = R.string.login_allowed),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(
                            horizontal = 28.dp,
                            vertical = 12.dp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun VerificationMessage() {
    Card(
        modifier = Modifier.clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(
                0xFFEEF1FF
            )
        )
    ) {
        Column(modifier = Modifier.padding(start = 12.dp, end = 40.dp)) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.verify_email),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.verification_warning),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun Retry(authViewModel: AuthViewModel) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = stringResource(id = R.string.code_question),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.width(30.dp))
        Row(
            modifier = Modifier.clickable { authViewModel.generateAndSendTwoFaCode() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.send_code_again).uppercase(),
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { })
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(id = R.string.send_code_again_ic),
                tint = Color.Blue
            )
        }
    }
}

@Composable
fun AuthCode(authViewModel: AuthViewModel) {
    val inputCode by authViewModel.inputTwoFaCode.collectAsState()
    val userData by authViewModel.userData.collectAsState()
    val userEmail = userData.email

    Column(modifier = Modifier.wrapContentSize()) {
        Text(
            text = stringResource(id = R.string.enter_code, userEmail),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.code_warning),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Textfield(value = inputCode,
            keyboardType = KeyboardType.Number,
            placeholder = stringResource(
                id = R.string.code
            ),
            onTextChanged = {
                if (it.length <= 6) {
                    authViewModel.onTwoFaCodeChanged(it)
                }
            })
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = stringResource(id = R.string.code_digit), fontSize = 12.sp)
    }
}