package com.alejandro.helphub.features.auth.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.alejandro.helphub.R

@Composable
fun MainScreen(
    navController: NavHostController
) {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 16.dp
                )
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        scaleX = 1.3f,
                        scaleY = 1.3f,
                        translationX = -100f,
                        translationY = 0f
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.vector),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(1f)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                LogoMainScreen()
                TitleMainScreen()
                Spacer(modifier = Modifier.height(20.dp))
                CardsRow()
                Spacer(modifier = Modifier.height(20.dp))
                AuthButtons(
                    text = stringResource(id = R.string.to_singUp).uppercase(),
                    onNextClick = { navController.navigate("SignUpCredsScreen") })
                Spacer(modifier = Modifier.height(8.dp))
                AuthButtons(text = stringResource(id = R.string.to_login).uppercase(),
                    onNextClick = { navController.navigate("LoginScreen") })
            }
        }
    }
}

@Composable
fun AuthButtons(
    text: String,
    onNextClick: () -> Unit
) {
    Button(
        onClick = { onNextClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = text
        )
    }
}

@Composable
fun RandomCards(
    userPfp: Painter,
    userName: String,
    userSurname1: String,
    postTitle: String,
    mode: String,
    availabilityHours: String,
    skillDescription: String,
    category: String
) {
    Card(
        modifier = Modifier
            .widthIn(max = 300.dp)
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFBFBFF)
        ),
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                Image(
                    painter = userPfp,
                    contentDescription = stringResource(id = R.string.user_photo_content_description),
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = "${userName} ${userSurname1}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = postTitle, fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Text(
                    text = mode,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.padding(end = 16.dp)) {
                Text(
                    text = stringResource(id = R.string.availability),
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = availabilityHours,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = skillDescription,
                    fontSize = 14.sp,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))
            Row() {
                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = category,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CardsRow() {
    val listState = rememberLazyListState()
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyRow(state = listState, modifier = Modifier.fillMaxWidth()) {
            item {
                RandomCards(
                    userPfp = painterResource(id = R.drawable.pfp_pedro),
                    userName = stringResource(id = R.string.pedro_name),
                    userSurname1 = stringResource(id = R.string.pedro_surname),
                    postTitle = stringResource(id = R.string.laura_postTitle),
                    mode = stringResource(id = R.string.pedro_mode),
                    availabilityHours = stringResource(id = R.string.pedro_availability),
                    skillDescription = stringResource(id = R.string.pedro_skill),
                    category = stringResource(id = R.string.pedro_category)
                )
            }
            item {
                RandomCards(
                    userPfp = painterResource(id = R.drawable.pfp_laura),
                    userName = stringResource(id = R.string.laura_name),
                    userSurname1 = stringResource(id = R.string.laura_surname),
                    postTitle = stringResource(id = R.string.laura_postTitle),
                    mode = stringResource(id = R.string.laura_mode),
                    availabilityHours = stringResource(id = R.string.laura_availability),
                    skillDescription = stringResource(id = R.string.laura_skill),
                    category = stringResource(id = R.string.laura_category)
                )
            }
            item {
                RandomCards(
                    userPfp = painterResource(id = R.drawable.pfp_joaquin),
                    userName = stringResource(id = R.string.joaquin_name),
                    userSurname1 = stringResource(id = R.string.joaquin_surname),
                    postTitle = stringResource(id = R.string.joaquin_postTitle),
                    mode = stringResource(id = R.string.joaquin_mode),
                    availabilityHours = stringResource(id = R.string.joaquin_availability),
                    skillDescription = stringResource(id = R.string.joaquin_skill),
                    category = stringResource(id = R.string.joaquin_category)
                )
            }
            item {
                RandomCards(
                    userPfp = painterResource(id = R.drawable.pfp_marta),
                    userName = stringResource(id = R.string.marta_name),
                    userSurname1 = stringResource(id = R.string.marta_surname),
                    postTitle = stringResource(id = R.string.marta_postTitle),
                    mode = stringResource(id = R.string.marta_mode),
                    availabilityHours = stringResource(id = R.string.marta_availability),
                    skillDescription = stringResource(id = R.string.marta_skill),
                    category = stringResource(id = R.string.marta_category)
                )
            }
            item {
                RandomCards(
                    userPfp = painterResource(id = R.drawable.pfp_diego),
                    userName = stringResource(id = R.string.diego_name),
                    userSurname1 = stringResource(id = R.string.diego_surname),
                    postTitle = stringResource(id = R.string.diego_postTitle),
                    mode = stringResource(id = R.string.diego_mode),
                    availabilityHours = stringResource(id = R.string.diego_availability),
                    skillDescription = stringResource(id = R.string.diego_skill),
                    category = stringResource(id = R.string.diego_category)
                )
            }
        }
    }
}

@Composable
fun TitleMainScreen() {
    Column(modifier = Modifier.offset(y = (-16).dp)) {
        Text(
            text = stringResource(id = R.string.main_screen_title1),
            fontSize = 34.sp,
            color = Color.White,
        )
        Text(
            text = stringResource(id = R.string.main_screen_title2),
            fontSize = 34.sp
        )
    }
}

@Composable
fun LogoMainScreen() {
    Image(
        painter = painterResource(id = R.drawable.helphub_blanco),
        contentDescription = stringResource(id = R.string.logo_content_description),
        modifier = Modifier.size(200.dp)
    )
}