package com.alejandro.helphub.presentation.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.alejandro.helphub.R
import com.alejandro.helphub.data.source.remote.dto.skill.CreateSkillDTO
import com.alejandro.helphub.presentation.navigation.BottomBarScreen

@Composable
fun EditSkillScreen(
    profileViewModel: ProfileViewModel,
    navController: NavHostController,
    skillId: String?
) {
    val listState = rememberLazyListState()
    val skillData by profileViewModel.skillData.collectAsState()

    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .statusBarsPadding()
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                contentDescription = stringResource(id = R.string.go_back),
                Modifier.clickable {
                    navController.navigate(BottomBarScreen.Profile.route)
                }
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.skills),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(0f)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        ) {
            item { Spacer(modifier = Modifier.height(30.dp)) }
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(
                            0x0FA58E8E
                        )
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        EditPostTitle(profileViewModel)
                        Spacer(modifier = Modifier.height(20.dp))
                        EditLevel(profileViewModel)
                        Spacer(modifier = Modifier.height(20.dp))
                        EditMode(profileViewModel)
                        Spacer(modifier = Modifier.height(20.dp))
                        EditSkillDescription(profileViewModel)
                        Spacer(modifier = Modifier.height(20.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .zIndex(1f)
                        ) {
                            EditCategory(profileViewModel)
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
            item {
                UpdateButton(
                    onNextClick = {
                        val createSkillDTO = CreateSkillDTO(
                            title = skillData.title,
                            description = skillData.description,
                            level = skillData.level,
                            mode = skillData.mode,
                            category = skillData.category
                        )
                        if (skillId != null) {
                            profileViewModel.updateSkill(
                                skillId,
                                createSkillDTO
                            )
                        }
                        navController.navigate(BottomBarScreen.Profile.route)
                    },
                    enabled = true
                )
            }
        }
    }
}

@Composable
fun EditCategory(profileViewModel: ProfileViewModel) {
    val skillData by profileViewModel.skillData.collectAsState()
    val expanded by profileViewModel.expanded.collectAsState()
    val selectedCategories by profileViewModel.selectedCategories.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.select_category),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { profileViewModel.toggleDropdown() }
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (skillData.category.isEmpty()) {
                                stringResource(
                                    id = R.string.categories
                                )
                            } else {
                                skillData.category.joinToString(", ")
                            },
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    }
                }
                if (expanded) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                Color.LightGray,
                                RoundedCornerShape(8.dp)
                            )
                            .shadow(elevation = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            val categories = listOf(
                                stringResource(id = R.string.animals),
                                stringResource(id = R.string.help),
                                stringResource(id = R.string.consultancy),
                                stringResource(id = R.string.design),
                                stringResource(id = R.string.languages),
                                stringResource(id = R.string.it),
                                stringResource(id = R.string.fixes),
                                stringResource(id = R.string.health),
                                stringResource(id = R.string.private_lessons),
                                stringResource(id = R.string.others)
                            )
                            categories.forEach { category ->
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        profileViewModel.onCategoryChecked(
                                            category,
                                            !selectedCategories.contains(
                                                category
                                            )
                                        )
                                    }
                                    .padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    ), verticalAlignment = CenterVertically

                                ) {
                                    Checkbox(
                                        checked = selectedCategories.contains(category),
                                        onCheckedChange = { checked ->
                                            profileViewModel.onCategoryChecked(
                                                category,
                                                checked
                                            )
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = category)
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EditSkillDescription(
    profileViewModel: ProfileViewModel,
) {
    val skillData by profileViewModel.skillData.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.skill_offer),
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = skillData.description,
            //skillData.description,
            onValueChange = {
                if (it.length <= 90) {
                    profileViewModel.updateSkillDescription(it)
                }
            },
            placeholder = {
                Text(
                    stringResource(id = R.string.skill_description),
                    modifier = Modifier.align(Alignment.Center)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
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
                id = R.string.character_limit_ninety,
                skillData.description.length
            ),
            fontSize = 18.sp,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp),
            color = Color.LightGray,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun EditMode(profileViewModel: ProfileViewModel) {
    val skillData by profileViewModel.skillData.collectAsState()

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
                    profileViewModel.updateLearningMode(it)
                },
            )
        }
        Box(modifier = Modifier.weight(2f)) {
            RadioButton(
                text = stringResource(id = R.string.face_to_face),
                selectedItem = skillData.mode,
                onItemSelected = {
                    profileViewModel.updateLearningMode(it)
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
fun EditLevel(
    profileViewModel: ProfileViewModel
) {
    val skillData by profileViewModel.skillData.collectAsState()

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
                selectedItem = skillData.level,
                onItemSelected = {
                    profileViewModel.updateSelectedLevel(it)
                },
            )
        }
        Box(modifier = Modifier.weight(4f)) {
            RadioButton(
                text = stringResource(id = R.string.amateur),
                selectedItem =
                skillData.level,
                onItemSelected = {
                    profileViewModel.updateSelectedLevel(it)
                })
        }
        Box(modifier = Modifier.weight(5f)) {
            RadioButton(
                text = stringResource(id = R.string.advanced),
                selectedItem = skillData.level,
                onItemSelected = {
                    profileViewModel.updateSelectedLevel(it)
                })
        }
    }
}

@Composable
fun EditPostTitle(profileViewModel: ProfileViewModel) {
    val skillData by profileViewModel.skillData.collectAsState()
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
    }
    Spacer(modifier = Modifier.height(20.dp))
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