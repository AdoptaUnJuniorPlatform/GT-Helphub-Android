package com.alejandro.helphub.presentation.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alejandro.helphub.R
import com.alejandro.helphub.domain.models.SkillData
import com.alejandro.helphub.domain.models.UserAuthData
import com.alejandro.helphub.domain.models.UserProfileData

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun ProfileScreen(
    //id: String?,
    //userId: String?,
    //profileViewModel: ProfileViewModel
) {
    /*
        LaunchedEffect(id, userId) {
            id?.let {
                profileViewModel.getProfileById(it)
            }
            userId?.let {
                profileViewModel.getUserById(userId)
                profileViewModel.getSkillsByUserId(userId)
            }
        }
        val userProfile = profileViewModel.userProfileData.collectAsState().value
        val user = profileViewModel.userAuthData.collectAsState().value

    */
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), contentAlignment = Alignment.TopCenter
        ) {
            Column {
                Spacer(modifier = Modifier.height(40.dp))
                UserCard(
                    // userProfile = userProfile,user=user
                )
                Spacer(modifier = Modifier.height(12.dp))
                ToggleButtons(
                    //  profileViewModel
                )
                // Spacer(modifier = Modifier.height(12.dp))

            }
        }
    }
}


@Composable
fun ToggleButtons(
    //profileViewModel: ProfileViewModel
) {
    val selectedOption = rememberSaveable { mutableStateOf("HABILIDADES") }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .border(2.dp, Color.LightGray, RoundedCornerShape(6.dp))
            .clip(RoundedCornerShape(6.dp))
            .background(Color.White)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { selectedOption.value = "HABILIDADES" }
                    .background(if (selectedOption.value == "HABILIDADES") MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "HABILIDADES",
                    fontWeight = FontWeight.Bold,
                    color = if (selectedOption.value == "HABILIDADES") MaterialTheme.colorScheme.primary else Color.Gray,
                    fontSize = 12.sp
                )
            }

            VerticalDivider(
                color = Color.LightGray,
                modifier = Modifier
                    .width(1.dp)
                    .height(40.dp)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { selectedOption.value = "VALORACIONES" }
                    .background(
                        if (selectedOption.value == "VALORACIONES") MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                    )
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "VALORACIONES",
                    fontWeight = FontWeight.Bold,
                    color = if (selectedOption.value == "VALORACIONES") MaterialTheme.colorScheme.primary else Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))

    if (selectedOption.value == "HABILIDADES") {
        UserSkills(
            //profileViewModel
        )
    } else {
        UserReviews()
    }
}


@Composable
fun UserSkills(
    // profileViewModel: ProfileViewModel
) {
    // val skillDataList by profileViewModel.skillDataList.collectAsState()
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Mis habilidades",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(24.dp))
        Button(
            onClick = {}, shape = RoundedCornerShape(6.dp),
            elevation = ButtonDefaults.buttonElevation(6.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            contentPadding = PaddingValues(start = 8.dp, end = 16.dp)
        ) {
            Icon(imageVector = Icons.Default.AddCircle, contentDescription = "")
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "NUEVA HABILIDAD", fontSize = 12.sp)
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
    SkillsRow(
        //  userSkillsList=skillDataList
    )

}

@Composable
fun SkillsRow(
    //  userSkillsList: List<SkillData>
) {
    val listState = rememberLazyListState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .offset((-12).dp)
    ) {
        LazyRow(state = listState, modifier = Modifier.fillMaxWidth()) {
            // items(userSkillsList) { userSkills ->
            //SkillCard(userSkills = userSkills)
            items(2) { userSkills ->
                SkillCard()
            }

        }
    }
}


@Composable
fun SkillCard(
    //userSkills: SkillData
) {
    Card(
        modifier = Modifier
            .width(270.dp)
            .padding(start = 16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Pintura al óleo",
                fontWeight = FontWeight.Bold,
                // userSkills.title,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(5.dp))
            Row {

                listOf(
                    stringResource(id = R.string.basic), stringResource(
                        id = R.string.amateur
                    ), stringResource(id = R.string.advanced)
                ).forEach { level ->
                    Box(
                        modifier = Modifier
                            .background
                            //(if(level== userSkills.level
                            // )Color.Blue else
                                (
                                Color.Transparent,
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
                            color =
                            // if(level==userSkills.level) Color.White else
                            Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.height(60.dp)) {
                Text(
                    text = "Aprende a preprarar un plato vegano delicioso y nutritivo (desde entrantes hasta postres)"
                    // userSkills.description
                    ,
                    fontSize = 15.sp

                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(width = 2.dp, color = Color.Gray)
                ) {
                    Text(text = "BORRAR")
                }
                Spacer(modifier = Modifier.width(6.dp))
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "EDITAR")
                }
            }

        }

    }

}

@Composable
fun UserReviews() {
    UserRating()
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = "Mis Reseñas", fontWeight = FontWeight.Bold, fontSize = 24.sp)
    Spacer(modifier = Modifier.height(12.dp))
    ReviewRow()
}

@Composable
fun ReviewRow() {
    val listState = rememberLazyListState()
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyRow(state = listState, modifier = Modifier.fillMaxWidth()) {
            item { ReviewCard() }
            item { ReviewCard() }
        }
    }
}

@Composable
fun ReviewCard() {
    Card(
        modifier = Modifier
            .widthIn(max = 300.dp)
            .padding(start = 16.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = "Las clases de pintura al óleo han sido maravillosas. ¡Además, intercambiar habilidades de cocina vendiaor sskdfjs  skdfjhsd kfhsd ksdjfh kdsjfhsk ksdjfh kjdfh ksskdjfh kjds fhsd kjsdfh kjsdfh sdkjdfh ksdhk kjsdfh ksdhfk.",
                    fontSize = 14.sp,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Image(
                    painter = painterResource(id = R.drawable.pfp_laura),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Laura García",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun UserRating() {
    Row {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(10.dp)
                )
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "5",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = "Valoración general",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "",
                    tint = Color.Yellow,
                    modifier = Modifier.size(12.dp)
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "",
                    tint = Color.Yellow,
                    modifier = Modifier.size(12.dp)
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "",
                    tint = Color.Yellow,
                    modifier = Modifier.size(12.dp)
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "",
                    tint = Color.Yellow,
                    modifier = Modifier.size(12.dp)
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "",
                    tint = Color.Yellow,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Excelente", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun UserCard(
    //userProfile: UserProfileData,user:UserAuthData
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0x0FA58E8E))
    ) {
        Column(
            modifier = Modifier.padding(
                start = 12.dp,
                end = 6.dp,
                bottom = 12.dp
            )
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Row {
                Text(
                    text = "Mi Perfil",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                //Icon
            }
            Spacer(modifier = Modifier.height(22.dp))
            Row {
                Box(modifier = Modifier.size(120.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.default_profile_icon),
                        contentDescription = "",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Red)
                    )
                }
                /*
                                Image(
                                    painter = painterResource(id = R.drawable.pfp_pedro),
                                    contentDescription = "",
                                    modifier = Modifier.size(140.dp)
                                )
                                */
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Juanita Pérez",
                        // "${user.nameUser} ${user.surnameUser}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Disponibilidad horaria",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier
                        .border(
                        width = 1.dp,
                            color = Color.Gray,
                        shape = RoundedCornerShape(6.dp))
                        .padding(horizontal = 10.dp, vertical = 2.dp)
                    ) { Text(text = "8:00 a 17:00") }
                            Spacer (modifier = Modifier.height(16.dp))
                            DayBox ()

                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    //.padding(12.dp)
                    .width(340.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(
                        0x39D5D3DA
                    )
                )
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = "Descripcion",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(modifier = Modifier.height(50.dp)) {
                        Text(
                            text = "Soy una artista que ama pintar, tengo 8 años de experiencia enseñando y pintando. Además me encantan los animales como las artes en general.",
                            fontSize = 13.sp
                            // userProfile.description
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Habilidades que me interesan",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            CategoriesOfInterest()
        }

    }
}

@Composable
fun CategoriesOfInterest() {
    Row {
        //userProfileData.selectedCategories.forEach { category ->
        listOf("Informática", "Idiomas", "Tutorias").forEach { category ->

            Box(
                modifier = Modifier

                    .background(
                        Color(0x39D5D3DA),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = category,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Composable
fun DayBox() {
    Row {
        listOf(
            "L",
            "M",
            "X",
            "J",
            "V",
            "S",
            "D"
        ).forEach { day ->
            Box(
                modifier = Modifier
                    .background(
                        color = //if (day == userProfileData.selectedLevel)
                        MaterialTheme.colorScheme.primary
                        //else Color.Transparent,
                        , shape = RoundedCornerShape(20.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.Transparent,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 5.dp, vertical = 4.dp)
            ) {
                Text(
                    text = day,
                    fontSize = 14.sp,
                    color = //if (level == userProfileData.selectedLevel)
                    Color.White
                    //else Color.Black
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
        }
    }
}
