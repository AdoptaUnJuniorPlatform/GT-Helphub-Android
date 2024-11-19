package com.alejandro.helphub.presentation.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.draw.clip
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
import com.alejandro.helphub.presentation.navigation.BottomBarScreen

@Composable
fun NewSkillScreen1(
    profileViewModel: ProfileViewModel,
    navController: NavHostController
) {
    var showCard by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .statusBarsPadding()
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                contentDescription = stringResource(id = R.string.go_back),
                Modifier.clickable { navController.navigate(BottomBarScreen.Profile.route) })
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = stringResource(id = R.string.skills), fontSize = 24.sp)
        }

    }) { innerPadding ->
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
                Spacer(modifier = Modifier.height(30.dp))
                SkillCounterCard(profileViewModel)
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(
                            0x0FA58E8E
                        )
                    )
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Spacer(modifier = Modifier.height(20.dp))
                        NewSkill()
                        Spacer(modifier = Modifier.height(20.dp))
                        NewPostTitle(
                            profileViewModel,
                            showCard = showCard,
                            onShowCardChange = { showCard = it })
                        Spacer(modifier = Modifier.height(20.dp))
                        NewSkillLevel(profileViewModel)
                        Spacer(modifier = Modifier.height(20.dp))
                        NewSkillMode(profileViewModel)
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }
                Spacer(modifier = Modifier.height(100.dp))
                NextButton(onNextClick = {
                    navController.navigate(
                        BottomBarScreen.NewSkillScreen2.route
                    )
                }, enabled = true)
            }
        }
    }
}

@Composable
fun NextButton(
    onNextClick: () -> Unit,
    enabled: Boolean
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {

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
                text = stringResource(id = R.string.next).uppercase(),
                color = if (enabled) Color.Blue else Color.LightGray
            )
        }
    }
}

@Composable
fun SkillCounterCard(profileViewModel: ProfileViewModel) {
    val skillDataList by profileViewModel.skillDataList.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = Color(0x4F87DF21))
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_success_green),
                    contentDescription = stringResource(id = R.string.success),
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "${stringResource(id = R.string.you_have)} ${skillDataList.size} ${stringResource(id = R.string._skills)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3F6810),
                    modifier = Modifier.align(
                        Alignment.CenterVertically
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text(
                    text = "Sigue sumando habilidades para hacer crecer esta comunidad",
                    fontSize = 14.sp,
                    color = Color(0xFF3F6810)
                )
            }
        }
    }
}

@Composable
fun NewSkillMode(profileViewModel: ProfileViewModel) {
    val skillData by profileViewModel.skillData.collectAsState()
    var selectedItem by remember { mutableStateOf(skillData.mode) }
    LaunchedEffect(Unit) {
        profileViewModel.resetLearningMode()
    }
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
                selectedItem = skillData.mode,
                onItemSelected = {
                    selectedItem = it
                    profileViewModel.updateLearningMode(it)
                },
            )
        }
        Box(modifier = Modifier.weight(1.3f)) {
            RadioButton(
                text = stringResource(id = R.string.face_to_face),
                selectedItem = skillData.mode,
                onItemSelected = {
                    selectedItem = it
                    profileViewModel.updateLearningMode(it)
                })
        }
        Box(modifier = Modifier.weight(0.8f))
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
fun NewSkillLevel(profileViewModel: ProfileViewModel) {
    val skillData by profileViewModel.skillData.collectAsState()
    var selectedItem by remember { mutableStateOf(skillData.level) }
    LaunchedEffect(Unit) {
        profileViewModel.resetSkillLevel()
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
        Box(modifier = Modifier.width(100.dp)) {
            NewSkillRadioButton(
                text = stringResource(id = R.string.basic),
                selectedItem = skillData.level,
                onItemSelected = {
                    selectedItem = it
                    profileViewModel.updateSelectedLevel(it)
                },
            )
        }
        Box(modifier = Modifier.width(100.dp)) {
            NewSkillRadioButton(
                text = stringResource(id = R.string.amateur),
                selectedItem = skillData.level,
                onItemSelected = {
                    selectedItem = it
                    profileViewModel.updateSelectedLevel(it)
                })
        }
        Box(modifier = Modifier.width(160.dp)) {
            NewSkillRadioButton(
                text = stringResource(id = R.string.advanced),
                selectedItem = skillData.level,
                onItemSelected = {
                    selectedItem = it
                    profileViewModel.updateSelectedLevel(it)
                })
        }
    }
}

@Composable
fun NewSkillRadioButton(
    text: String,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .width(170.dp)
            .padding(end = 4.dp)
            .border(
                width = 2.dp,
                color = if (selectedItem == text) Color.Blue else Color.DarkGray,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        androidx.compose.material3.RadioButton(
            selected = (selectedItem == text),
            onClick = { onItemSelected(text) },
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.Blue,
                unselectedColor = Color.DarkGray
            )
        )
        Text(
            text = text, fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(10.dp))
    }
}

@Composable
fun NewPostTitle(
    profileViewModel: ProfileViewModel,
    showCard: Boolean,
    onShowCardChange: (Boolean) -> Unit
) {
    val skillData by profileViewModel.skillData.collectAsState()
    LaunchedEffect(Unit) {
        profileViewModel.resetPostTitle()
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.width(150.dp)) {
            Text(
                text = stringResource(id = R.string.post_title),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(100.dp))
        Box(modifier = Modifier
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
    Spacer(modifier = Modifier.height(6.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = skillData.title,
            singleLine = true,
            onValueChange = {

                if (it.length <= 20) {
                    profileViewModel.updatePostTitle(it)
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
                skillData.title.length
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
fun NewSkill() {
    Text(
        text = stringResource(id = R.string.new_skill),
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = stringResource(id = R.string.add_skills),
        fontSize = 16.sp
    )
}