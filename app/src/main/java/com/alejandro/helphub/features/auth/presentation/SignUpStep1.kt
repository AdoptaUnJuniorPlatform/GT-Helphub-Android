package com.alejandro.helphub.features.auth.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.alejandro.helphub.R

@Preview(showBackground = true)
@Composable
fun SignUpStep1() {
    val listState = rememberLazyListState()
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(0f)
            ) {
                item { RegisterHeader() }
                item { Divider() }
                item { StepOne() }
                item { ScreenTitle() }
                item { Spacer(modifier = Modifier.height(20.dp)) }
                item { Description() }
                item { TextBox() }
                item { Location() }
                item { Spacer(modifier = Modifier.height(20.dp)) }
                item { StepButtons() }
                item{EndBox()}
            }
        }
    }
}

@Composable
fun EndBox() {
    Box(modifier=Modifier.height(40.dp))
}

@Composable
fun StepButtons() {
    Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
        Box(modifier= Modifier
            .weight(1f)
            .align(Alignment.CenterVertically)) {
            Text(text = stringResource(id = R.string.go_back))
        }
        Button(
            onClick = { },
            modifier= Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = stringResource(id = R.string.next),
                color = MaterialTheme.colorScheme.primary
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
        modifier = Modifier.padding(start = 16.dp)
    )
    OutlinedTextField(
        value = postalCode,
        onValueChange = {postalCode=it},
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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
        { Icon(imageVector = Icons.Default.Mail, contentDescription = stringResource(
            id = R.string.postal_code_content_description
        )) }
    )
    Text(
        text = stringResource(id = R.string.postal_code_instruction),
        fontSize = 16.sp,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        color = Color.Gray
    )
}

@Composable
fun TextBox() {
    var text by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                if (it.length <= 255) {
                    text = it
                }
            },
            placeholder = { Text(stringResource(id =R.string.description_placeholder)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(146.dp),
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
            text = stringResource(id = R.string.character_limit,text.length),
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
        fontSize = 25.sp,
        color = Color.Black,
        modifier = Modifier.padding(start = 16.dp)
    )
}

@Composable
fun ScreenTitle() {
    Text(
        text = stringResource(id = R.string.step_1),
        fontSize = 40.sp,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 16.dp)
    )
}

@Composable
fun StepOne() {
    Column(modifier = Modifier.absolutePadding(left = 16.dp, right = 16.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.step_one),
                contentDescription = "",
                modifier = Modifier
                    .size(350.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun Divider() {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .absolutePadding(left = 16.dp, right = 16.dp),
        color = Color.LightGray,
        thickness = 2.dp
    )
}

@Composable
fun RegisterHeader() {
    Column(modifier = Modifier.absolutePadding(left = 16.dp, right = 16.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .padding(bottom = 24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.helphub_morado),
                modifier = Modifier
                    .size(180.dp)
                    .align(Alignment.TopCenter),
                contentDescription = ""
            )
            // Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Cuentanos un poco\nmás sobre ti",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                modifier = Modifier.align(
                    Alignment.BottomCenter
                )
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}