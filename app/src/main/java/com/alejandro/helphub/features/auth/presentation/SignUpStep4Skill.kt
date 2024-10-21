package com.alejandro.helphub.features.auth.presentation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alejandro.helphub.R

@Composable
fun SignUpStep4Skill(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController
) {
    var showCard by remember { mutableStateOf(false) }
    var showDataCard by remember { mutableStateOf(false) }
    val userData by authViewModel.userData.collectAsState()
    val isNextButtonEnabled= userData.skillDescription.isNotEmpty()&& userData.selectedCategory!=null
    Box(modifier = Modifier.fillMaxSize()) {
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
                    StepFourProgressIndicator()
                    StepFourTitle()
                    Spacer(modifier = Modifier.height(20.dp))
                    SkillTextBox(authViewModel, showCard = showCard,
                        onShowCardChange = { showCard = it })
                    Spacer(modifier = Modifier.height(20.dp))
                    CategorySelection(authViewModel)
                    Spacer(modifier = Modifier.height(58.dp))
                    StepButtons(
                        onBackClick = { navController.navigate("SignUpStep4Post") },
                        onNextClick = {
                            showDataCard = true
                        },
                        enabled= isNextButtonEnabled &&!showDataCard
                    )
                }

            }

        }
        if (showDataCard) {
            OpeSkillCard(authViewModel,navController)

        }
    }
}

@Composable
fun OpeSkillCard(authViewModel: AuthViewModel,navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    )
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        DataCard(authViewModel, navController)
    }
}


@Composable
fun DataCard(authViewModel: AuthViewModel, navController: NavHostController) {
    val userData by authViewModel.userData.collectAsState()
    Log.d(
        "CongratulationsBox",
        "name: ${userData.name}," +
                "surname1: ${userData.surname1}," +
                "surname2: ${userData.surname2}," +
                "email: ${userData.email}," +
                "password: ${userData.password}," +
                "countryCode:${userData.countryCode}" +
                "phoneNumber: ${userData.phoneNumber}" +
                "userDescription: ${userData.userDescription}" +
                "postalCode: ${userData.postalCode}, " +
                "userPhotoUri: ${userData.userPhotoUri}, " +
                "availability: ${userData.availability}, " +
                "days: ${userData.selectedDays}, " +
                "postTitle: ${userData.postTitle}, " +
                "selectedLevel: ${userData.selectedLevel}, " +
                "mode: ${userData.mode}, " +
                "skilLDescription: ${userData.skillDescription}, " +
                "selectedCategory: ${userData.selectedCategory},"
    )
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .offset(y = (-20).dp)
            .border(
                width = 2.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 15.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.congrats),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = "¡Felicidades!",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = "Ya tienes tu primera habilidad cargada en tu cuenta",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
        }
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .padding(horizontal = 30.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(6.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color(
                    0xFFEEF1FF
                )
            )
        ) {
            Box(modifier = Modifier.width(300.dp)) {
                Text(
                    text = "Puedes editarla o eliminarla cuando quieras desde la sección de tu perfil.",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(
                            vertical = 12.dp,
                            horizontal = 28.dp
                        )
                )
            }
        }
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Vista previa",
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFBFBFF)
                ),
                elevation = CardDefaults.cardElevation(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(text = userData.postTitle, fontSize = 24.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = userData.mode ?: "Modo no seleccionado",
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(text = "Nivel", fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.padding(horizontal = 26.dp)) {
                    //borrar de aqui abajo
                    listOf("Básico", "Medio", "Avanzado").forEach { level ->
                        Box(
                            modifier = Modifier
                                .background(
                                    color = if (level == userData.selectedLevel) Color.Blue else Color.Transparent,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = level,
                                fontSize = 14.sp,
                                color = if (level == userData.selectedLevel) Color.White else Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.padding(horizontal = 26.dp)) {

                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = userData.selectedCategory
                                ?: "No seleccionado", fontSize = 14.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { navController.navigate("SignUpStep5") },
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue
                )
            ) {
                Text(text = "CONTINUAR")
            }
            Spacer(modifier = Modifier.height(24.dp))


        }
    }
}



@Composable
fun CategorySelection(authViewModel: AuthViewModel) {
    val userData by authViewModel.userData.collectAsState()
    val expanded by authViewModel.expanded.collectAsState()
    val categories = authViewModel.categories

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
                .padding(16.dp)
                .background(Color.LightGray)
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .clickable { authViewModel.toggleDropdown() },
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (userData.selectedCategory.isNullOrEmpty()) stringResource(
                        id = R.string.categories
                    ) else userData.selectedCategory!!,
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
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(text = category) },
                        onClick = {
                            authViewModel.updateSelectedCategory(category)
                            authViewModel.toggleDropdown()
                        })
                }
            }
        }
    }
}

@Composable
fun SkillTextBox(
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
            text = stringResource(id = R.string.skill_offer),
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
                    text = stringResource(id = R.string.photo_advice),
                    color = Color.Blue,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Icon(
                    imageVector = if (showCard) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = stringResource(
                        id = R.string.photo_advice
                    ),
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
                    text = stringResource(id = R.string.how_to_text),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.how_to_text_message),
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.how_to_text_details),
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.how_to_text_value),
                    fontSize = 16.sp,
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
            value = userData.skillDescription,
            onValueChange = {
                if (it.length <= 200) {
                    authViewModel.updateSkillDescription(it)
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
    }
}

