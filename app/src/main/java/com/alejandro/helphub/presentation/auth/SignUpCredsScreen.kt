package com.alejandro.helphub.presentation.auth

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.alejandro.helphub.R
import com.alejandro.helphub.utils.ResultStatus

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpCredsScreen(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    val listState = rememberLazyListState()
    val userData by authViewModel.userAuthData.collectAsState()
    val isChecked: Boolean by authViewModel.isCheckBoxChecked.collectAsState(
        initial = false
    )
    val isSignUpEnabled by authViewModel.isSignUpButtonEnabled.collectAsState(
        initial = false
    )
    val isLoading by authViewModel.isLoading.collectAsState(initial = false)
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        )
        {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(0f)
                ) {
                    item { Logo() }
                    item { SignUpText() }
                    item {
                        CustomTextField(
                            value = userData.nameUser,
                            onValueChange = {
                                authViewModel.updateUserName(it)
                            },
                            placeholder = stringResource(id = R.string.name)
                        )
                    }
                    item {
                        CustomTextField(
                            value = userData.surnameUser,
                            onValueChange =
                            { authViewModel.updateUserSurname1(it) },
                            placeholder = stringResource(id = R.string.surname1)
                        )
                    }
                    item {
                        CustomTextField(
                            value = userData.surname2,
                            onValueChange =
                            { authViewModel.updateUserSurname2(it) },
                            placeholder = stringResource(id = R.string.surname2)
                        )
                    }
                    item { PhoneTextfield(authViewModel) }
                    item {
                        EmailTextfield(userData.email) {
                            authViewModel.updateUserEmail(it)
                        }
                    }
                    item {
                        PasswordTextfield(authViewModel)
                    }
                    item { Spacer(modifier = Modifier.height(12.dp)) }
                    item { PasswordReminder() }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                    item {
                        PhoneSwitch(
                            authViewModel = authViewModel
                        )
                    }
                    item {
                        PrivacyCheck(
                            isChecked = isChecked,
                            authViewModel = authViewModel,
                            navController
                        )
                    }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                    item {
                        SignUpButton(
                            authViewModel,
                            isEnabled = isSignUpEnabled,
                            navController = navController
                        )
                    }
                    item { ToLogin(navController = navController) }
                    item { Spacer(modifier = Modifier.height(24.dp)) }
                }
            }
        }
    }
}

@Composable
fun ToLogin(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.account_question),
            color = Color.Gray,
            fontSize = 16.sp
        )
        Text(
            text = stringResource(id = R.string.login),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clickable { navController.navigate("LoginScreen") },
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Blue
        )
    }
}

@Composable
fun SignUpButton(
    authViewModel: AuthViewModel,
    isEnabled: Boolean,
    navController: NavHostController
) {
    val twoFaStatus by authViewModel.twoFaStatus.collectAsState()
    val context = LocalContext.current
    Button(
        onClick = {
            authViewModel.generateAndSendTwoFaCode()
            navController.navigate("TwofaRegisterScreen")
        },
        enabled = isEnabled,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = stringResource(id = R.string.signup).uppercase())
    }
    when (twoFaStatus) {
        is ResultStatus.Success -> {
            authViewModel.resetTwoFaStatus()
            navController.navigate("TwofaRegisterScreen")
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
fun PrivacyCheck(isChecked: Boolean, authViewModel: AuthViewModel, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { newValue ->
                    authViewModel.onCheckBoxCheckedChanged(newValue)
                },
                enabled = true
            )
        }
        Column(modifier = Modifier.align(Alignment.Bottom).clickable { navController.navigate("PrivacyScreen") }) {
            Text(text = buildAnnotatedString {
                append(stringResource(id = R.string.i_agree_to_the))
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(
                        stringResource(id = R.string.terms_and_conditions)
                    )
                }
            })
        }
    }
}

@Composable
fun PasswordReminder() {
    Text(
        text = stringResource(id = R.string.password_condition),
        fontSize = 14.sp,
        color = Color.DarkGray,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
fun PasswordTextfield(authViewModel: AuthViewModel) {
    val userData by authViewModel.userAuthData.collectAsState()
    var passwordVisibility by remember { mutableStateOf(false) }
    val isPasswordValid by authViewModel.isPasswordValid.collectAsState()
    val borderColor = if (isPasswordValid) Color.Gray else Color.Red
    OutlinedTextField(
        value = userData.password,
        onValueChange = { authViewModel.onPasswordChanged(it) },
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
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedBorderColor = borderColor,
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
fun EmailTextfield(email: String, onTextChanged: (String) -> Unit) {
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
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray
        )
    )
}

@Composable
fun PhoneSwitch(authViewModel: AuthViewModel) {
    val isSwitched: Boolean by authViewModel.isSwitchChecked.collectAsState(
        initial = false
    )
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Switch(
                checked = isSwitched,
                onCheckedChange = { newValue ->
                    authViewModel.onSwitchCheckedChanged(newValue)
                }
            )
        }
        Column(modifier = Modifier.weight(4f)) {
            Text(
                text = stringResource(id = R.string.activate_call),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.show_number),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun PhoneTextfield(authViewModel: AuthViewModel) {
    val countries = com.alejandro.helphub.data.source.local.CountryProvider.countries
    val userData by authViewModel.userAuthData.collectAsState()
    val expanded by authViewModel.isExpanded.collectAsState(false)
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(12.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val selectedCountry =
            countries.find { it.code == userData.countryCode.ifEmpty { "+34" } }
        Box(
            modifier = Modifier
                .weight(1f)
                .clickable { authViewModel.toggleExpanded() }
                .padding(start = 8.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                selectedCountry
                    ?.let {
                        Image(
                            painter = painterResource(id = it.flagResId),
                            contentDescription = it.name,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = userData.countryCode.ifEmpty { "+34" },
                    color = Color.Gray
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = stringResource(id = R.string.dropdown_flags_content_description),
                    tint = Color.Gray
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { authViewModel.toggleExpanded() }
                ) {
                    countries.forEach { country ->
                        DropdownMenuItem(
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = country.flagResId),
                                        contentDescription = country.name,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = "${country.name} (${country.code})")
                                }
                            },
                            onClick = {
                                authViewModel.updateSelectedCountry(country)
                                authViewModel.toggleExpanded()
                            }
                        )
                    }
                }
            }
        }
        Spacer(
            modifier = Modifier
                .width(1.dp)
                .height(30.dp)
                .background(Color.Gray)
        )
        OutlinedTextField(
            value = userData.phone,
            onValueChange = {
                if (it.length <= 9) {
                    authViewModel.updatePhoneNumber(it)
                }
            },
            modifier = Modifier
                .weight(3f)
                .fillMaxHeight()
                .padding(start = 8.dp),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.mobile_numer),
                    color = Color.Gray
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            maxLines = 1,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Gray,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            textStyle = TextStyle(
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            ),
        )
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isSingleLine: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .offset(y = (-8).dp),
        shape = RoundedCornerShape(12.dp),
        placeholder = { Text(text = placeholder) },
        singleLine = isSingleLine,
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
fun SignUpText() {
    Text(
        text = stringResource(id = R.string.signing_up),
        fontSize = 28.sp,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun Logo() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clip(
                    RoundedCornerShape(
                        bottomStart = 46.dp,
                        bottomEnd = 46.dp
                    )
                )
                .background(MaterialTheme.colorScheme.primary)
                .zIndex(1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.helphub_blanco),
                contentDescription = stringResource(id = R.string.logo_content_description),
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(180.dp)
            )
        }
    }
}