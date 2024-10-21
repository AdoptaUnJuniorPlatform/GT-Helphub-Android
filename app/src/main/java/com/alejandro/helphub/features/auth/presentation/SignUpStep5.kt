package com.alejandro.helphub.features.auth.presentation

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alejandro.helphub.R


@Composable
fun SignUpStep5(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val userData by authViewModel.userData.collectAsState()
    val isNextEnabled=userData.categoriesOfInterest.isNotEmpty()
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
                PopularCategories(authViewModel)
                Spacer(modifier = Modifier.height(16.dp))
                MoreCategories(authViewModel)
                Spacer(modifier = Modifier.height(76.dp))
                StepButtons(
                    onBackClick = { navController.navigate("SignUpStep4Skill") },
                    onNextClick = {  },
                    enabled = isNextEnabled
                )

            }
        }
    }
}

@Composable
fun MoreCategories(authViewModel: AuthViewModel) {
    val userData by authViewModel.userData.collectAsState()
    val expanded by authViewModel.expanded.collectAsState()
    val selectedCategories = userData.categoriesOfInterest ?: emptyList()
    val availableCategories =
        authViewModel.categories.filterNot { it in selectedCategories }


    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.more_categories),
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
                    text = if (selectedCategories.isEmpty()) stringResource(
                        id = R.string.categories
                    ) else selectedCategories.joinToString(","),
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
                availableCategories.forEach { category ->
                    val isSelected = selectedCategories.contains(category)
                    DropdownMenuItem(
                        text = { Text(text = category) },
                        onClick = {
                            if (isSelected) {
                                authViewModel.removeCategoryOfInterest(category)
                            } else {
                                authViewModel.updateSelectedCategoriesOfInterest(
                                    category
                                )
                            }
                            authViewModel.toggleDropdown()
                        })
                }
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
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = category,
            fontSize = 14.sp,
            color = if (isSelected) Color.White else Color.Black
        )
    }
    Spacer(modifier = Modifier.width(12.dp))
}


@Composable
fun PopularCategories(
    authViewModel: AuthViewModel
) {
    val categories = authViewModel.popularCategories
    val userData by authViewModel.userData.collectAsState()


    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.popular_categories),
                fontSize = 22.sp,
                modifier = Modifier.align(Alignment.CenterVertically),
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        categories.chunked(3).forEach { categoryRow ->
            Row {
                categoryRow.forEach { category ->
                    val isSelected =
                        userData.categoriesOfInterest.contains(category)
                    CategoryBox(category = category, isSelected = isSelected,
                        onItemSelected = { isSelected ->
                            authViewModel.updateCategoriesOfInterest(category, isSelected)
                        }
                    )

                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
        Log.d("InterestInfo", "categories: ${userData.categoriesOfInterest}")
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
