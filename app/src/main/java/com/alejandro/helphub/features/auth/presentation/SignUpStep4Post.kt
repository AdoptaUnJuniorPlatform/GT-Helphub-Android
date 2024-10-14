package com.alejandro.helphub.features.auth.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.alejandro.helphub.R

@Preview(showBackground = true)
@Composable
fun SignUpStep4Post() {
    val listState = rememberLazyListState()
    var showDialog by remember { mutableStateOf(false) }
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
                .blur(if (showDialog) 12.dp else 0.dp)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(0f)
            ) {
                item { RegisterHeader() }
                item { StepFourProgressIndicator() }
                item { StepFourTitle() }
                item { Spacer(modifier = Modifier.height(20.dp)) }
                item { AddSkill() }
                item { Spacer(modifier = Modifier.height(20.dp)) }
                item {
                    PostTitle(
                        showDialog = showDialog,
                        onShowDialogChange = { showDialog = it })
                }
                item { Spacer(modifier = Modifier.height(20.dp)) }
                item { Level() }
                item { Spacer(modifier = Modifier.height(20.dp)) }
                item { Mode() }
                item { Spacer(modifier = Modifier.height(30.dp)) }
                item { StepButtons(onBackClick = {}, onNextClick = {}) }
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        text = stringResource(id = R.string.title_post_dialog),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                text = {
                    Column {
                        Text(
                            text = stringResource(id = R.string.meditation_dialog),
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = stringResource(id = R.string.cv_dialog),
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = stringResource(id = R.string.cooking_dialog),
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = stringResource(id = R.string.gym_dialog),
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { showDialog = false },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Blue,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = stringResource(id = R.string.confirm_button))
                    }
                },
                shape = RoundedCornerShape(6.dp),
                containerColor = Color.White
            )
        }

    }
}

@Composable
fun Mode() {
    var selectedItem by remember { mutableStateOf("Online") }
    Row {
        Text(
            text = stringResource(id = R.string.mode),
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Row {
        Box(modifier = Modifier.weight(1f)) {
            RadioButton(
                text = stringResource(id = R.string.online),
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it },
            )
        }
        Box(modifier = Modifier.weight(2f)) {
            RadioButton(
                text = stringResource(id = R.string.face_to_face),
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it })
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
    Row {
        Text(text = stringResource(id = R.string.warning), fontSize = 14.sp)
    }
}

@Composable
fun Level() {
    var selectedItem by remember { mutableStateOf("Avanzado") }
    Row {
        Text(
            text = stringResource(id = R.string.level),
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Row {
        Box(modifier = Modifier.weight(4f)) {
            RadioButton(
                text = stringResource(id = R.string.basic),
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it },
            )
        }
        Box(modifier = Modifier.weight(4f)) {
            RadioButton(
                text = stringResource(id = R.string.amateur),
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it })
        }
        Box(modifier = Modifier.weight(5f)) {
            RadioButton(
                text = stringResource(id = R.string.advanced),
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it })
        }
    }
}


@Composable
fun PostTitle(showDialog: Boolean, onShowDialogChange: (Boolean) -> Unit) {
    var text by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.post_title),
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(modifier = Modifier
            .padding(end = 12.dp)
            .align(Alignment.CenterVertically)
            .clickable { onShowDialogChange(true) }) {
            Row(horizontalArrangement = Arrangement.End) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = stringResource(
                        id = R.string.post_title_examples_dialog
                    ),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(20.dp)
                )
                Text(
                    text = stringResource(id = R.string.post_title_examples_dialog),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                if (it.length <= 20) {
                    text = it
                }
            },
            placeholder = {
                Text(
                    stringResource(id = R.string.skill_placeholder),
                    modifier = Modifier.align(Alignment.Center)
                )
            },
            modifier = Modifier
                .fillMaxWidth(),
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
            text = stringResource(
                id = R.string.character_limit_twenty,
                text.length
            ), fontSize = 18.sp,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp),
            color = Color.LightGray,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun AddSkill() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.add_first_skill),
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = stringResource(id = R.string.add_skills),
        color = Color.Gray, fontSize = 18.sp
    )
}

@Composable
fun StepFourTitle() {
    Text(
        text = stringResource(id = R.string.step_4),
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = stringResource(id = R.string.step_4_title),
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.primary,
    )
}

@Composable
fun StepFourProgressIndicator() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.step_four),
                contentDescription = "",
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}