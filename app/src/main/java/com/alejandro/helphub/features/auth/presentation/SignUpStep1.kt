package com.alejandro.helphub.features.auth.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun SignUpStep1(
    authViewModel: AuthViewModel,
    navController:NavHostController) {
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
                Description()
                TextBox()
                Spacer(modifier = Modifier.height(20.dp))
                Location()
                Spacer(modifier = Modifier.height(70.dp))
                StepButtons(
                    onBackClick = {navController.navigate("SignUpCredsScreen")},
                    onNextClick = {navController.navigate("SignUpStep2")}
                )
            }
        }
    }
}

@Composable
fun StepButtons(
    onBackClick:()->Unit,
    onNextClick:()->Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = { onBackClick()},
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .wrapContentWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(1.dp, color = Color.DarkGray)
        ) {
            Text(
                text = stringResource(id = R.string.go_back),
                color = Color.DarkGray
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { onNextClick() },
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .wrapContentWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(1.dp, color = Color.Blue)
        ) {
            Text(
                text = stringResource(id = R.string.next),
                color = Color.Blue
            )
        }
    }
}

@Composable
fun Location() {
    var postalCode by remember { mutableStateOf("") }
    Text(
        text = stringResource(id = R.string.location),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
    )
    Spacer(modifier = Modifier.height(10.dp))
    OutlinedTextField(
        value = postalCode,
        onValueChange = { postalCode = it },
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
fun TextBox() {
    var text by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                if (it.length <= 255) {
                    text = it
                }
            },
            placeholder = { Text(stringResource(id = R.string.description_placeholder)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            shape = RoundedCornerShape(12.dp),
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
            text = stringResource(id = R.string.character_limit, text.length),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp),
            color = Color.LightGray,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun Description() {
    Text(
        text = stringResource(id = R.string.user_description_title),
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
    Column{
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
    Column{
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