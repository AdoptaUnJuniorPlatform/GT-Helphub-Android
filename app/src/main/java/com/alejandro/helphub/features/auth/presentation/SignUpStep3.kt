package com.alejandro.helphub.features.auth.presentation

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Checkbox
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alejandro.helphub.R

@Composable
fun SignUpStep3(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController
) {
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
                StepThreeProgressIndicator()
                StepThreeTitle()
                Spacer(modifier = Modifier.height(16.dp))
                Availability()
                Spacer(modifier = Modifier.height(16.dp))
                AvailabilityOptions(authViewModel)
                Spacer(modifier = Modifier.height(10.dp))
                DaySelection(authViewModel)
                Spacer(modifier = Modifier.height(14.dp))
                StepButtons(
                    onBackClick = { navController.navigate("SignUpStep2") },
                    onNextClick = { navController.navigate("SignUpStep4Post") }
                )
            }
        }

    }
}

@Composable
fun DaySelection(authViewModel: AuthViewModel) {
    val expanded by authViewModel.expanded.collectAsState(initial = false)
    val selectedDays by authViewModel.selectedDays.collectAsState(initial = emptyList())
    val daysOfWeek = authViewModel.daysOfWeek
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.days),
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = stringResource(id = R.string.select_more_days),
        color = Color.Gray, fontSize = 18.sp,
        modifier = Modifier.padding(end = 12.dp)
    )
    Spacer(modifier = Modifier.height(10.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.LightGray)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .clickable { authViewModel.toggleDropdown() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (selectedDays.isEmpty()) stringResource(id = R.string.select_day)
                else selectedDays.joinToString(","),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { authViewModel.toggleDropdown() }
        ) {
            daysOfWeek.forEach { day ->
                var isChecked = selectedDays.contains(day)
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = {
                                    authViewModel.onDayChecked(day, it)
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = day)
                        }
                    },
                    onClick = { })
            }
        }
    }
}

@Composable
fun RadioButton(
    text: String,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .width(170.dp)
            .padding(end = 8.dp)
            .border(
                width = 2.dp,
                color = if (selectedItem == text) Color.Blue else Color.DarkGray,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        RadioButton(
            selected = (selectedItem == text),
            onClick = { onItemSelected(text) },
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.Blue,
                unselectedColor = Color.DarkGray
            )
        )
        Text(
            text = text,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(10.dp))
    }
}


@Composable
fun AvailabilityOptions(authViewModel: AuthViewModel) {
    val userData by authViewModel.userData.collectAsState()
    var selectedItem by remember { mutableStateOf(userData.availability ?: "") }
    Column(modifier = Modifier.fillMaxWidth()) {
        Row {
            Text(
                text = stringResource(id = R.string.availability), fontSize = 22.sp,
                modifier = Modifier.align(Alignment.CenterVertically),
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Box {
                RadioButton(
                    text = stringResource(id = R.string.eight_to_two),
                    selectedItem = selectedItem,
                    onItemSelected = { selectedItem = it }
                )

            }
            Box {
                RadioButton(
                    text = stringResource(id = R.string.three_to_five),
                    selectedItem = selectedItem,
                    onItemSelected = { selectedItem = it
                        authViewModel.updateAvailability(it)}
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Box {
                RadioButton(
                    text = stringResource(id = R.string.five_to_nine),
                    selectedItem = selectedItem,
                    onItemSelected = { selectedItem = it
                        authViewModel.updateAvailability(it)}
                )
            }
            //Spacer(modifier = Modifier.weight(1f))
            Box {
                RadioButton(
                    text = stringResource(id = R.string.eight_to_five),
                    selectedItem = selectedItem,
                    onItemSelected = { selectedItem = it
                        authViewModel.updateAvailability(it)}
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Box {
                RadioButton(
                    text = stringResource(id = R.string.availability_title),
                    selectedItem = selectedItem,
                    onItemSelected = { selectedItem = it
                        authViewModel.updateAvailability(it)}
                )
            }
        }
    }
}



@Composable
fun Availability() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.availability_question),
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = stringResource(id = R.string.step_three_description),
        color = Color.Gray, fontSize = 18.sp,
        modifier = Modifier.padding(end = 12.dp)
    )
}

@Composable
fun StepThreeTitle() {
    Text(
        text = stringResource(id = R.string.step_3),
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = stringResource(id = R.string.step_3_title),
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.primary,
    )
}

@Composable
fun StepThreeProgressIndicator() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.step_three),
                contentDescription = stringResource(id = R.string.step_3),
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}