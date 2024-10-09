package com.alejandro.helphub.features.auth.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.alejandro.helphub.R

@Preview(showBackground = true)
@Composable
fun SignUpStep2() {
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
                item { StepTwoMotto() }
                item { Spacer(modifier = Modifier.height(30.dp)) }
                item { Divider() }
                item { StepTwoProgressIndicator() }
                item { StepTwoTitle() }
                item { Spacer(modifier = Modifier.height(20.dp)) }
                item { ChooseImage() }
                item { UploadImage() }
                item { Examples() }
                item { Spacer(modifier = Modifier.height(20.dp)) }
                item { StepButtons() }
                item { EndBox() }
            }
        }
    }
}

@Composable
fun Examples() {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
        var showDialog by remember { mutableStateOf(false) }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        text = stringResource(id = R.string.photo_dialog),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                text = {
                    Text(
                        text = stringResource(id = R.string.photo_indications_dialog),
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                },
                confirmButton = {
                    Button(onClick = { showDialog=false }) {
                        Text(text = stringResource(id = R.string.confirm_button))
                    }
                },
                modifier = Modifier.padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                containerColor = Color.White
            )
        }
        Row {
            Text(
                text = stringResource(id = R.string.example_first_line),
                fontSize = 16.sp
            )
        }
        Row {
            Text(
                text = stringResource(id = R.string.example_second_line),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = stringResource(id = R.string.dialog_text),
                modifier = Modifier
                    .size(16.dp)
                    .clickable {
                        showDialog = true
                    }
            )
        }
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profiles),
                contentDescription = stringResource(id = R.string.profile_examples)
            )
        }
    }
}

@Composable
fun UploadImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .width(328.dp)
            .height(210.dp)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = "",
                tint = Color.Gray,
                modifier = Modifier.size(52.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.upload_photo),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.photobox_text),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { },
                modifier = Modifier,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                elevation = ButtonDefaults.buttonElevation(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.upload_photo).uppercase(),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ChooseImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.choose_photo),
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    )
    {
        Text(
            text = stringResource(id = R.string.step_two_description),
            color = Color.Gray, fontSize = 20.sp
        )
    }
}

@Composable
fun StepTwoTitle() {
    Text(
        text = stringResource(id = R.string.step_2),
        fontSize = 40.sp,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 16.dp)
    )
}

@Composable
fun StepTwoProgressIndicator() {
    Column(modifier = Modifier.absolutePadding(left = 16.dp, right = 16.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.step_two),
                contentDescription = "",
                modifier = Modifier
                    .size(350.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun StepTwoMotto() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Text(
            text = stringResource(id = R.string.step_2_title),
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(start = 50.dp, end = 50.dp)
        )
    }
}