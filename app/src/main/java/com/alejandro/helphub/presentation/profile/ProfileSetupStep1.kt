package com.alejandro.helphub.presentation.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.alejandro.helphub.R
import com.alejandro.helphub.presentation.navigation.BottomBarScreen

@Composable
fun ProfileSetupStep1(
    profileViewModel: ProfileViewModel,
    navController: NavHostController,
    email:String?

) {

    val isStep2Enabled by profileViewModel.isNavigationToStep2Enabled.collectAsState(
        initial = false
    )
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
                    .fillMaxWidth()
                    .zIndex(0f)
            ) {
                RegisterHeader()
                StepOneProgressIndicator()
                StepOneTitle()
                Spacer(modifier = Modifier.height(20.dp))
                Description(text = stringResource(id = R.string.user_description_title))
                TextBox(profileViewModel)
                Spacer(modifier = Modifier.height(20.dp))
                Location(profileViewModel)
                Spacer(modifier = Modifier.height(104.dp))
                StepButtons(
                    onBackClick = { navController.navigate(BottomBarScreen.Home.route) },
                    onNextClick = { navController.navigate(BottomBarScreen.ProfileSetupStep2.createRoute(email!!)) },
                    enabled = isStep2Enabled
                )
            }
        }
    }
}

@Composable
fun StepButtons(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    enabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = { onBackClick() },
            enabled = true,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .wrapContentWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(1.dp, color = Color.DarkGray)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                modifier = Modifier
                    .size(16.dp)
                    .offset((-8).dp),
                tint = Color.Black,
                contentDescription = stringResource(id = R.string.go_back)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(id = R.string.go_back),
                color = Color.DarkGray
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { onNextClick() },
            enabled = enabled,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .wrapContentWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            ),
            border = BorderStroke(
                1.dp,
                color = if (enabled) Color.Blue else Color.LightGray
            )
        ) {
            Text(
                text = stringResource(id = R.string.next),
                color = if (enabled) Color.Blue else Color.LightGray
            )
        }
    }
}

@Composable
fun Location(profileViewModel: ProfileViewModel) {
    val userProfileData by profileViewModel.userProfileData.collectAsState()
    Text(
        text = stringResource(id = R.string.location),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
    )
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        value = userProfileData.location,
        onValueChange = {
            if (it.length <= 5 && it.all { char -> char.isDigit() }) {
                profileViewModel.updatePostalCode(it)
            }
        },
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        placeholder = { Text(text = stringResource(id = R.string.postal_code)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.LightGray,
            focusedPlaceholderColor = Color.LightGray,
            unfocusedPlaceholderColor = Color.LightGray,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color.LightGray
        ),
        leadingIcon =
        {
            Icon(
                imageVector = Icons.Default.Mail,
                contentDescription = stringResource(
                    id = R.string.postal_code_content_description
                )
            )
        }
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = stringResource(id = R.string.postal_code_instruction),
        fontSize = 14.sp,
        color = Color.Gray
    )
}

@Composable
fun TextBox(profileViewModel: ProfileViewModel) {
    val userProfileData by profileViewModel.userProfileData.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = userProfileData.description,
            onValueChange = {
                if (it.length <= 160) {
                    profileViewModel.updateUserDescription(it)
                }
            },
            placeholder = { Text(stringResource(id = R.string.description_placeholder)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            shape = RoundedCornerShape(14.dp),
            textStyle = TextStyle(fontSize = 16.sp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.LightGray,
                focusedPlaceholderColor = Color.LightGray,
                unfocusedPlaceholderColor = Color.LightGray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray
            )
        )
        Text(
            text = stringResource(
                id = R.string.character_limit,
                userProfileData.description.length
            ),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp),
            color = Color.LightGray,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun Description(text:String) {
    Text(
        text=text,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
fun StepOneTitle() {
    Text(
        text = stringResource(id = R.string.step_1),
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = stringResource(id = R.string.step_1_title),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
    )
}

@Composable
fun StepOneProgressIndicator() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.step_one),
                contentDescription = stringResource(id = R.string.step_1),
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun RegisterHeader() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                painter = painterResource(id = R.drawable.helphub_morado),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp)
                    .size(60.dp)
                    .align(Alignment.BottomCenter),
                contentDescription = stringResource(id = R.string.logo_content_description)
            )
        }
    }
}