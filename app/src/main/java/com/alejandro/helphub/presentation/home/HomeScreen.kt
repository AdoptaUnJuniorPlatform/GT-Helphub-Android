package com.alejandro.helphub.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.alejandro.helphub.R


@Composable
fun Home(homeViewModel: HomeViewModel) {
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
LaunchedEffect(Unit) {

    homeViewModel.getSkillsForAllCategories(categories)
}


    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .statusBarsPadding()
        ) {
            Spacer(modifier = Modifier.width(40.dp))
            Image(
                painter = painterResource(id = R.drawable.helphub_morado),
                contentDescription = "",
                modifier = Modifier
                    .width(130.dp)
                    .aspectRatio(16 / 9f),
            )
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
                HomeSearchBar()
                Spacer(modifier = Modifier.height(20.dp))
                HomeTitle()
                Spacer(modifier = Modifier.height(20.dp))
                RecommendedUsers()

            }
        }
    }
}

@Composable
fun RecommendedUsers() {
    Text(text = "Recomendados", fontSize = 24.sp)
    Spacer(modifier = Modifier.height(10.dp))
    HomeCards()
}

@Composable
fun HomeCards() {
    val listState = rememberLazyListState()
LazyRow(state=listState, modifier = Modifier.fillMaxWidth()){
    //Crear lazy row para tarjetas
}

    Card(
        modifier = Modifier.width(300.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFBFBFF)
        ),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.padding(horizontal = 24.dp)) {
            Box(modifier = Modifier.size(50.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.pfp_laura),
                    //rememberAsyncImagePainter(userProfileData.profileImage),
                    contentDescription = stringResource(id = R.string.user_photo_content_description),
                    modifier = Modifier
                        .size(124.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Juanita Pérez",
                //"${userAuthData.nameUser} ${userAuthData.surnameUser}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "Cuidado de animales"
                //   skillData.title
                , fontSize = 24.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            //Aqui hay que poner online o location
            Text(
                text = "14005"
                // userProfileData.location
                ,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "Medio"
                // skillData.mode
                ,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.padding(horizontal = 26.dp)) {
            listOf(
                stringResource(id = R.string.basic), stringResource(
                    id = R.string.amateur
                ), stringResource(id = R.string.advanced)
            ).forEach { level ->
                Box(
                    modifier = Modifier
                        .background(
                            color = if (level == "Medio"
                            // skillData.level
                            )
                                Color.Blue else Color.Transparent,
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
                        color = if (level == "Medio"
                        //  skillData.level
                        ) Color.White else Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = stringResource(id = R.string.availability),
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(50.dp))
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
                    text = "8:00 a 14:00",
                    //userProfileData.preferredTimeRange,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "Aprende a preparar un plato vegano delicioso y nutritivo (desde entrantes hasta postres"
                // skillData.description
                ,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.padding(horizontal = 26.dp)) {
            //skillData.category.forEach { category ->
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
                    text = "Animales",
                    //category,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
    Spacer(modifier = Modifier.height(26.dp))
}


@Composable
fun HomeTitle() {
    Row {
        Column(modifier = Modifier.width(160.dp)) {
            Text(text = "Categorías y habilidades", fontSize = 28.sp)
        }
        Spacer(modifier = Modifier.width(100.dp))
        Text(
            text = "FILTROS", modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSearchBar() {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = text,
        onQueryChange = {
            text = it
        },
        onSearch = {
            active = false
        },
        active = false,
        onActiveChange = { },
        placeholder = {
            Row {
                Spacer(modifier = Modifier.width(30.dp))
                Text(text = "¿Qué estás buscando?")
            }
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = ""
            )
        }
    ) {
        Spacer(modifier = Modifier.size(0.dp))
    }
}