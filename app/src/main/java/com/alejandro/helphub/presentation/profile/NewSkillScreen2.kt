package com.alejandro.helphub.presentation.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.alejandro.helphub.R
import com.alejandro.helphub.presentation.navigation.BottomBarScreen


@Composable
fun NewSkillScreen2(
    profileViewModel: ProfileViewModel,
    navController: NavHostController,
) {
    var showCard by remember { mutableStateOf(false) }
    Scaffold(topBar = {
        Row(modifier = Modifier
            .padding(top = 20.dp)
            .statusBarsPadding()) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                contentDescription = "",
                Modifier.clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "Habilidades", fontSize = 24.sp)
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

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(
                            0x0FA58E8E
                        )
                    )
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Spacer(modifier = Modifier.height(30.dp))
                        SkillTextBox(
                            profileViewModel = profileViewModel,
                            showCard = showCard,
                            onShowCardChange = { showCard = it })
                        Spacer(modifier = Modifier.height(30.dp))
                        CategorySelection(profileViewModel)
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }
                Spacer(modifier = Modifier.height(230.dp))
                SaveButton(
                    onBackClick = {
                        navController.navigate(
                            BottomBarScreen.NewSkillScreen1.route
                        )
                    },
                    onNextClick = {
                        profileViewModel.createSkill()
                        navController.navigate(BottomBarScreen.Profile.route)
                    },
                    enabled = true
                )
            }
        }
    }
}

@Composable
fun SaveButton(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    enabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = { onBackClick() },
            enabled = true,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .wrapContentWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(1.dp, color = Color.DarkGray)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                modifier = Modifier
                    .size(16.dp)
                    .offset((-8).dp),
                tint = Color.Black,
                contentDescription = stringResource(id = R.string.go_back)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(id = R.string.go_back),
                color = Color.DarkGray
            )
        }
        Spacer(modifier = Modifier.weight(1f))
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
                text = "GUARDAR",
                color = if (enabled) Color.Blue else Color.LightGray
            )
        }
    }

}