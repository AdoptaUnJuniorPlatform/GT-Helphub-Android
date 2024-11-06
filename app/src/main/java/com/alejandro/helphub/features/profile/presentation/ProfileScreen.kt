package com.alejandro.helphub.features.profile.presentation

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alejandro.helphub.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun ProfileScreen() {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ), contentAlignment = Alignment.TopCenter
        ) {
            Column {
                Spacer(modifier = Modifier.height(40.dp))
                UserCard()
                Spacer(modifier = Modifier.height(12.dp))
                ToggleButtons()
                Spacer(modifier = Modifier.height(12.dp))

            }
        }
    }
}


@Composable
fun ToggleButtons() {
    val selectedOption = remember { mutableStateOf("HABILIDADES") }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            //.padding(16.dp)
            .border(2.dp, Color.LightGray, RoundedCornerShape(6.dp))
            .clip(RoundedCornerShape(6.dp))
            .background(Color.White)
    ) {
        Row(modifier = Modifier) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { selectedOption.value = "HABILIDADES" }
                    .background(
                        if (selectedOption.value == "HABILIDADES") MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                    )
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
        UserSkills()
    } else {
        UserReviews()
    }
}

@Composable
fun UserSkills() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Mis habilidades",
            fontSize = 20.sp,
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
            Text(text = "NUEVA HABILIDAD")
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
    SkillsRow()

}

@Composable
fun SkillsRow() {
    val listState = rememberLazyListState()
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyRow(state = listState, modifier = Modifier.fillMaxWidth()) {
            item { SkillCard() }
            item { SkillCard() }
        }
    }
}

@Composable
fun SkillCard() {
    Card(
        modifier = Modifier
            .widthIn(max = 300.dp)
            .padding(start = 16.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Pintura al óleo")
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                //userProfileData.selectedCategories.forEach { category ->
                listOf("Básico", "Medio", "Avanzado").forEach { category ->
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .background(Color.LightGray)
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
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Aprende a preparar un plato vegano delicioso y nutritivo (desde entrantes hasta postres)",
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
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
                Image(painter = painterResource(id = R.drawable.pfp_laura), contentDescription = "")
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Laura García", fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
fun UserCard() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(
                start = 12.dp,
                end = 6.dp,
                bottom = 12.dp
            )
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(
                    text = "Mi Perfil",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                //Icon
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Image(
                    painter = painterResource(id = R.drawable.pfp_pedro),
                    contentDescription = "",
                    modifier = Modifier.size(140.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Juanita Perez",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Disponibilidad horaria")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "de 8 a 17")
                    Spacer(modifier = Modifier.height(16.dp))
                    DayBox()

                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    //.padding(12.dp)
                    .width(340.dp),
                colors = CardDefaults.cardColors(containerColor = Color.LightGray)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "Descripcion", fontSize = 16.sp)
                    Text(text = "Soy una artista que ama pintar, tengo 8 años de experiencia enseñando y pintando. Además me encantan lso animales como las artes en general.")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Habilidades que me interesan", fontSize = 16.sp)
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
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.LightGray)
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
                        color = //if (level == userProfileData.selectedLevel)
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
