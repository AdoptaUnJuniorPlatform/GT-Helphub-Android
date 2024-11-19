package com.alejandro.helphub.presentation.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.alejandro.helphub.R
import com.alejandro.helphub.presentation.navigation.BottomBarScreen
import com.alejandro.helphub.presentation.navigation.RootNavGraphObjects

@Composable
fun ProfileSetupStep5(
    profileViewModel: ProfileViewModel,
    navController: NavHostController,
    email:String?
) {
    val isHomeEnabled by profileViewModel.isNavigationToHomeEnabled.collectAsState(
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
                StepFiveProgressIndicator()
                Step5Title()
                Spacer(modifier = Modifier.height(16.dp))
                Learn()
                Spacer(modifier = Modifier.height(16.dp))
                PopularCategories(profileViewModel, text = stringResource(id = R.string.choose_categories))
                Spacer(modifier = Modifier.height(16.dp))
                Spacer(modifier = Modifier.height(134.dp))
                StepButtons(
                    onBackClick = { navController.navigate(BottomBarScreen.ProfileSetupStep4b.createRoute(email!!)) },
                    onNextClick = {
                        profileViewModel.createProfile()
                        navController.navigate(RootNavGraphObjects.Home.route) },
                    enabled = isHomeEnabled
                )
            }
        }
    }
}

@Composable
fun CategoryBox(
    category: String,
    isSelected: Boolean,
    onItemSelected: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = if (isSelected) Color.Blue else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) Color.Transparent else Color.Gray,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onItemSelected(!isSelected) }
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = category,
            fontSize = 16.sp,
            color = if (isSelected) Color.White else Color.Black
        )
    }
    Spacer(modifier = Modifier.width(12.dp))
}

@Composable
fun PopularCategories(
    profileViewModel: ProfileViewModel,
    text:String
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
    val chunkedCategories = listOf(
        categories.take(4),
        categories.subList(4, 7),
        categories.takeLast(3)
    )
    val userProfileData by profileViewModel.userProfileData.collectAsState()
    val selectedCategories by profileViewModel.selectedCategoriesOfInterest.collectAsState()
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = text,
                fontSize = 22.sp,
                modifier = Modifier.align(Alignment.CenterVertically),
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.select_up_to_3_categories),
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterVertically),
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        chunkedCategories.forEach { categoryRow ->
            Row {
                categoryRow.forEach { category ->
                    val isSelected =
                        selectedCategories.contains(category)
                    CategoryBox(category = category, isSelected = isSelected,
                        onItemSelected = {
                            profileViewModel.onCategoriesOfInterestChecked(
                                category,
                                it
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
        Log.d("InterestInfo", "categories: ${userProfileData.interestedSkills}")
    }
}

@Composable
fun Learn() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.learning_question),
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = stringResource(id = R.string.step_five_description),
        color = Color.Gray, fontSize = 18.sp,
        modifier = Modifier.padding(end = 12.dp)
    )
}

@Composable
fun Step5Title() {
    Text(
        text = stringResource(id = R.string.step_5),
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
fun StepFiveProgressIndicator() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.step_five),
                contentDescription = stringResource(id = R.string.step_5),
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}