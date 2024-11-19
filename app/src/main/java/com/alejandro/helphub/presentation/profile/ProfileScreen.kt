package com.alejandro.helphub.presentation.profile

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.alejandro.helphub.R
import com.alejandro.helphub.domain.models.SkillData
import com.alejandro.helphub.domain.models.UserAuthData
import com.alejandro.helphub.domain.models.UserProfileData
import com.alejandro.helphub.presentation.navigation.BottomBarScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

@Composable
fun ProfileScreen(
    id: String?,
    userId: String?,
    profileViewModel: ProfileViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    LaunchedEffect(id, userId) {
        id?.let {
            profileViewModel.getProfileById(it)
        }
        userId?.let {
            profileViewModel.getUserById(userId)
            profileViewModel.getSkillsByUserId(userId)
            profileViewModel.getProfileImage(context)
        }
    }
    val skillId = profileViewModel.getSkillId()
    Log.d("ProfileScreen", "Skill ID: $skillId")
    val userProfile = profileViewModel.userProfileData.collectAsState().value
    val user = profileViewModel.userAuthData.collectAsState().value
    Log.d(
        "ProfileScreen",
        "Comprobado valores ${userProfile.description} ${userProfile.preferredTimeRange} ${userProfile.interestedSkills}"
    )
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column {
                Spacer(modifier = Modifier.height(40.dp))
                UserCard(
                    userProfile = userProfile,
                    user = user,
                    profileViewModel,
                    navController,
                    id,
                    userId
                )
                Spacer(modifier = Modifier.height(12.dp))
                ToggleButtons(
                    profileViewModel, navController
                )
            }
        }
    }
}

@Composable
fun ToggleButtons(
    profileViewModel: ProfileViewModel, navController: NavHostController
) {
    val selectedOption = rememberSaveable { mutableStateOf("skills") }
    Box(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .border(2.dp, Color.LightGray, RoundedCornerShape(6.dp))
            .clip(RoundedCornerShape(6.dp))
            .background(Color.White)
    ) {
        Row {
            Box(modifier = Modifier
                .weight(1f)
                .clickable { selectedOption.value = "skills" }
                .background(if (selectedOption.value == "skills") MaterialTheme.colorScheme.primaryContainer else Color.Transparent)
                .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(id = R.string.skills).uppercase(),
                    fontWeight = FontWeight.Bold,
                    color = if (selectedOption.value == "skills") MaterialTheme.colorScheme.primary else Color.Gray,
                    fontSize = 12.sp
                )
            }
            VerticalDivider(
                color = Color.LightGray,
                modifier = Modifier
                    .width(1.dp)
                    .height(40.dp)
            )
            Box(modifier = Modifier
                .weight(1f)
                .clickable { selectedOption.value = "reviews" }
                .background(
                    if (selectedOption.value == "reviews") MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                )
                .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(id = R.string.reviews).uppercase(),
                    fontWeight = FontWeight.Bold,
                    color = if (selectedOption.value == "reviews") MaterialTheme.colorScheme.primary else Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    if (selectedOption.value == "skills") {
        UserSkills(
            profileViewModel, navController
        )
    } else {
        UserReviews()
    }
}

@Composable
fun UserSkills(
    profileViewModel: ProfileViewModel, navController: NavHostController
) {
    val skillDataList by profileViewModel.skillDataList.collectAsState()
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = stringResource(id = R.string.my_skills),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(24.dp))
        Button(
            onClick = { navController.navigate(BottomBarScreen.NewSkillScreen1.route) },
            shape = RoundedCornerShape(6.dp),
            elevation = ButtonDefaults.buttonElevation(6.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            contentPadding = PaddingValues(start = 8.dp, end = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = stringResource(
                    id = R.string.add_skills
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = R.string.new_skill).uppercase(),
                fontSize = 12.sp
            )
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
    SkillsRow(
        userSkillsList = skillDataList,
        profileViewModel = profileViewModel,
        navController = navController
    )
}

@Composable
fun SkillsRow(
    userSkillsList: List<SkillData>,
    profileViewModel: ProfileViewModel,
    navController: NavHostController
) {
    val listState = rememberLazyListState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .offset((-12).dp)
    ) {
        LazyRow(state = listState, modifier = Modifier.fillMaxWidth()) {
            items(userSkillsList) { userSkills ->
                SkillCard(
                    userSkills = userSkills,
                    profileViewModel = profileViewModel,
                    onEditClick = { skillId ->
                        Log.d(
                            "SkillsRow",
                            "Edit button clicked. Skill ID: $skillId"
                        )
                        navController.navigate(
                            BottomBarScreen.EditSkillScreen.createRoute(
                                skillId
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun SkillCard(
    userSkills: SkillData,
    profileViewModel: ProfileViewModel,
    onEditClick: (String) -> Unit
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
                text = userSkills.title, fontWeight = FontWeight.Bold,
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
                            .background(
                                if (level == userSkills.level) Color.Blue else Color.Transparent,
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
                            color = if (level == userSkills.level) Color.White else Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.height(60.dp)) {
                Text(
                    text = userSkills.description, fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Button(
                    onClick = { profileViewModel.deleteSkill(userSkills.id) },
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(width = 2.dp, color = Color.Gray)
                ) {
                    Text(text = stringResource(id = R.string.delete).uppercase())
                }
                Spacer(modifier = Modifier.width(6.dp))
                Button(
                    onClick = {
                        profileViewModel.selectSkillForEdit(userSkills)
                        onEditClick(userSkills.id)
                    },
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue, contentColor = Color.White
                    )
                ) {
                    Text(text = stringResource(id = R.string.edit).uppercase())
                }
            }
        }
    }
}

@Composable
fun UserReviews() {
    UserRating()
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = stringResource(id = R.string.my_reviews),
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )
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
                    text = stringResource(id = R.string.lore_ipsum), //Aquí irá la reseña del usuario
                    fontSize = 14.sp,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Image(
                    painter = painterResource(id = R.drawable.pfp_laura), //Aqui irá la foto de perfil del usuario que reseña
                    contentDescription = stringResource(id = R.string.pfp_content_description)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Laura García", //Aqui irá el nombre del usuario que hace la review
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
                text = "5", //Aqui irá la valoración del usuario
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = stringResource(id = R.string.overall_review),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Star()
                Star()
                Star()
                Star()
                Star()
            }
        }
    }
}

@Composable
fun Star() {
    Icon(
        imageVector = Icons.Default.Star,
        contentDescription = stringResource(id = R.string.star_content_description),
        tint = Color.Yellow,
        modifier = Modifier.size(12.dp)
    )
}

@Composable
fun UserCard(
    userProfile: UserProfileData,
    user: UserAuthData,
    profileViewModel: ProfileViewModel,
    navController: NavHostController,
    id: String?,
    userId: String?
) {
    val profileImageBytes by profileViewModel.profileImage.collectAsState()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0x0FA58E8E))
    ) {
        Column(
            modifier = Modifier.padding(
                start = 12.dp, end = 6.dp, bottom = 12.dp
            )
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.my_profile),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                )
                IconButton(
                    onClick = {
                        if (id != null && userId != null) {
                            navController.navigate(
                                BottomBarScreen.EditProfileScreen.createRoute(
                                    id,
                                    userId
                                )
                            )
                        }
                    },
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color.Blue,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .size(30.dp), contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(id = R.string.edit_content_description),
                            tint = Color.White,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                Box(modifier = Modifier.size(120.dp)) {
                    profileImageBytes?.let {
                        Log.d(
                            "UserCard",
                            "ByteArray size: ${profileImageBytes?.size ?: 0}"
                        )
                        val painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(it) // Convertimos el ByteArray a Input stream
                                .build()
                        )
                        val painterState = painter.state
                        if (painterState is AsyncImagePainter.State.Error) {
                            Log.e(
                                "UserCard",
                                "Error al cargar la imagen: ${painterState.result.throwable}"
                            )
                        }
                        Image(
                            painter = painter,
                            contentDescription = "stringResource(id = R.string.profile_image)",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(
                                    RoundedCornerShape(12.dp)
                                ),
                            contentScale = ContentScale.Crop
                        )
                    } ?: run { // Si no hay imagen, mostramos un placeholder
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Gray)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "${user.nameUser} ${user.surnameUser}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(id = R.string.preferred_time_range),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 2.dp)
                    ) { Text(text = userProfile.preferredTimeRange) }

                    Spacer(modifier = Modifier.height(16.dp))
                    DayBox(userProfile)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier

                    .width(340.dp), colors = CardDefaults.cardColors(
                    containerColor = Color(
                        0x39D5D3DA
                    )
                )
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = stringResource(id = R.string.description),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(modifier = Modifier.height(70.dp)) {
                        Text(
                            text = userProfile.description, fontSize = 13.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.interested_skills),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            CategoriesOfInterest(userProfile)
        }
    }
}

@Composable
fun CategoriesOfInterest(userProfile: UserProfileData) {
    Row {
        userProfile.interestedSkills.forEach { category ->
            Box(
                modifier = Modifier
                    .background(
                        Color(0x39D5D3DA), shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = category, fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Composable
fun DayBox(userProfile: UserProfileData) {
    val dayMapping = mapOf(
        stringResource(id = R.string.monday) to stringResource(id = R.string.monday_letter),
        stringResource(id = R.string.tuesday) to stringResource(id = R.string.tuesday_letter),
        stringResource(id = R.string.wednesday) to stringResource(id = R.string.wednesday_letter),
        stringResource(id = R.string.thursday) to stringResource(id = R.string.thursday_letter),
        stringResource(id = R.string.friday) to stringResource(id = R.string.friday_letter),
        stringResource(id = R.string.saturday) to stringResource(id = R.string.saturday_letter),
        stringResource(id = R.string.sunday) to stringResource(id = R.string.sunday_letter)
    )
    val dayInitials = listOf(
        stringResource(id = R.string.monday_letter),
        stringResource(id = R.string.tuesday_letter),
        stringResource(id = R.string.wednesday_letter),
        stringResource(id = R.string.thursday_letter),
        stringResource(id = R.string.friday_letter),
        stringResource(id = R.string.saturday_letter),
        stringResource(id = R.string.sunday_letter)
    )
    Row {
        dayInitials.forEach { day ->
            Box(
                modifier = Modifier
                    .background(
                        color = if (dayMapping.any { it.value == day && it.key in userProfile.selectedDays }) MaterialTheme.colorScheme.primary
                        else Color.Transparent,
                        shape = RoundedCornerShape(20.dp)
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
                    color = if (dayMapping.any { it.value == day && it.key in userProfile.selectedDays }) Color.White
                    else Color.Black
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
        }
    }
}
