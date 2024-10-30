package com.alejandro.helphub.features.auth.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.alejandro.helphub.R

@Composable
fun SignUpStep4Post(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    val listState = rememberLazyListState()
    var showCard by remember { mutableStateOf(false) }
    val isStep4SkillEnabled by authViewModel.isNavigationToStep4SkillEnabled.collectAsState(
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
                    PostTitle(authViewModel,
                        showCard = showCard,
                        onShowCardChange = { showCard = it })
                }
                item { Spacer(modifier = Modifier.height(20.dp)) }
                item { Level(authViewModel) }
                item { Spacer(modifier = Modifier.height(20.dp)) }
                item { Mode(authViewModel) }
                item { Spacer(modifier = Modifier.height(22.dp)) }
                item {
                    StepButtons(
                        onBackClick = { navController.navigate("SignUpStep3") },
                        onNextClick = { navController.navigate("SignUpStep4Skill") },
                        enabled = isStep4SkillEnabled
                    )
                }
            }
        }
    }
}

@Composable
fun Mode(authViewModel: AuthViewModel) {
    val userData by authViewModel.userData.collectAsState()
    var selectedItem by remember { mutableStateOf(userData.mode ?: "") }
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
                onItemSelected = {
                    selectedItem = it
                    authViewModel.updateLearningMode(it)
                },
            )
        }
        Box(modifier = Modifier.weight(2f)) {
            RadioButton(
                text = stringResource(id = R.string.face_to_face),
                selectedItem = selectedItem,
                onItemSelected = {
                    selectedItem = it
                    authViewModel.updateLearningMode(it)
                })
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
    Row {
        Text(
            text = stringResource(id = R.string.warning),
            fontSize = 14.sp, fontStyle = FontStyle.Italic
        )
    }
}

@Composable
fun Level(authViewModel: AuthViewModel) {
    val userData by authViewModel.userData.collectAsState()
    var selectedItem by remember {
        mutableStateOf(
            userData.selectedLevel ?: ""
        )
    }
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
                onItemSelected = {
                    selectedItem = it
                    authViewModel.updateSelectedLevel(it)
                },
            )
        }
        Box(modifier = Modifier.weight(4f)) {
            RadioButton(
                text = stringResource(id = R.string.amateur),
                selectedItem = selectedItem,
                onItemSelected = {
                    selectedItem = it
                    authViewModel.updateSelectedLevel(it)
                })
        }
        Box(modifier = Modifier.weight(5f)) {
            RadioButton(
                text = stringResource(id = R.string.advanced),
                selectedItem = selectedItem,
                onItemSelected = {
                    selectedItem = it
                    authViewModel.updateSelectedLevel(it)
                })
        }
    }
}

@Composable
fun PostTitle(
    authViewModel: AuthViewModel,
    showCard: Boolean,
    onShowCardChange: (Boolean) -> Unit
) {
    val userData by authViewModel.userData.collectAsState()
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
            .clickable { onShowCardChange(!showCard) }) {
            Row(horizontalArrangement = Arrangement.End) {
                Text(
                    text = stringResource(id = R.string.post_title_examples_dialog).uppercase(),
                    color = Color.Blue,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Icon(
                    imageVector =
                    if (showCard) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = stringResource(
                        id = R.string.post_title_examples_dialog
                    ).uppercase(),
                    tint = Color.Blue,
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
    AnimatedVisibility(
        visible = showCard,
        enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
        exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEEF1FF))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(id = R.string.title_post_dialog),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.meditation_dialog),
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.cv_dialog),
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.cooking_dialog),
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.gym_dialog),
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = userData.postTitle,
            singleLine = true,
            onValueChange = {
                if (it.length <= 20) {
                    authViewModel.updatePostTitle(it)
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
                userData.postTitle.length
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
                contentDescription = stringResource(id = R.string.step_4),
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}