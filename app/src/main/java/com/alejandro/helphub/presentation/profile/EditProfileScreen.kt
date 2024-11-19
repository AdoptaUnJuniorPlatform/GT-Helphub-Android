package com.alejandro.helphub.presentation.profile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.alejandro.helphub.R
import com.alejandro.helphub.data.source.remote.dto.profile.CreateProfileDTO
import com.alejandro.helphub.presentation.navigation.BottomBarScreen


@Composable
fun EditProfileScreen(
    profileViewModel: ProfileViewModel,
    navController: NavHostController,
    id: String?,
    userId: String?
) {
    val listState = rememberLazyListState()
    val userProfileData by profileViewModel.userProfileData.collectAsState()
    val context = LocalContext.current
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    Log.d("EditProfileScreen", "comprobando si id esta vacio $userId")

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
                Modifier.clickable { navController.navigate(BottomBarScreen.Profile.route) }
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(id = R.string.edit_profile),
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
                        Description(text = "Descripción del usuario")
                        TextBox(profileViewModel)
                        Spacer(modifier = Modifier.height(20.dp))
                        Location(profileViewModel)
                        Spacer(modifier = Modifier.height(20.dp))
                        ProfilePicTitle()
                        Spacer(modifier = Modifier.height(10.dp))
                        UploadNewPhoto(
                            profileViewModel,
                            photoUri = photoUri,
                            onPhotoUriChanged = { newUri -> photoUri = newUri })
                        Spacer(modifier = Modifier.height(10.dp))
                        AvailabilityOptions(profileViewModel)
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .zIndex(1f)
                        ) {
                            UpdateDaySelection(profileViewModel = profileViewModel)
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        UpdatePopularCategories(
                            profileViewModel = profileViewModel,
                            text = stringResource(id = R.string.learning_question)
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(40.dp)) }
            item {
                UpdateButton(
                    onNextClick = {
                        val createProfileDTO = CreateProfileDTO(
                            location = userProfileData.location,
                            description = userProfileData.description,
                            interestedSkills = userProfileData.interestedSkills,
                            selectedDays = userProfileData.selectedDays,
                            preferredTimeRange = userProfileData.preferredTimeRange
                        )
                        if (id != null) {
                            if (photoUri != null && userId != null) photoUri?.let { uri ->
                                profileViewModel.updateUserPhotoUriToUpdate(
                                    id = id,
                                    userId = userId,
                                    photoUri = uri,
                                    context = context
                                )
                                profileViewModel.updateProfileImage(
                                    id = userId,
                                    userId = userId,
                                    photoUri = uri,
                                    context = context
                                )
                            } else {
                                Log.e(
                                    "EditProfileScreen",
                                    "Error: id o userId es nulo"
                                )
                            }
                            profileViewModel.updateProfile(id, createProfileDTO)
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
fun UpdatePopularCategories(
    profileViewModel: ProfileViewModel,
    text: String
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
        categories.take(3),
        categories.subList(3, 6),
        categories.subList(6, 9),
        categories.takeLast(1)
    )
    val userProfileData by profileViewModel.userProfileData.collectAsState()

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
                        userProfileData.interestedSkills.contains(category)
                    CategoryBox(category = category, isSelected =
                    isSelected,
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
fun UpdateButton(
    onNextClick: () -> Unit,
    enabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
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
                text = stringResource(id = R.string.save).uppercase(),
                color = if (enabled) Color.Blue else Color.LightGray
            )
        }
    }
}

@Composable
fun UploadNewPhoto(
    profileViewModel: ProfileViewModel,
    photoUri: Uri?,
    onPhotoUriChanged: (Uri?) -> Unit
) {
    val profileImageBytes by profileViewModel.profileImage.collectAsState()
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            onPhotoUriChanged(it)
        }
    }
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.size(100.dp)) {
                Image(
                    painter =
                    when {
                        photoUri != null -> rememberAsyncImagePainter(photoUri)
                        profileImageBytes != null -> rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(profileImageBytes)
                                .build()
                        )
                        else -> painterResource(id = R.drawable.default_profile_icon)},
                    contentDescription = stringResource(id = R.string.user_photo_content_description),
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .then(
                            if (photoUri == null&&profileImageBytes==null) Modifier.border(
                                12.dp,
                                Color.Gray,
                                CircleShape
                            )
                            else Modifier
                        ),
                    contentScale = ContentScale.Crop
                )
                if (photoUri != null) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_check_circle),
                        contentDescription = stringResource(id = R.string.ic_check_photo),
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.TopEnd)
                            .offset((-4).dp, (-4).dp)
                            .background(Color.White, CircleShape)
                            .border(1.dp, Color.White, CircleShape)
                    )
                }
            }
            Button(
                onClick = {
                    galleryLauncher.launch("image/*")
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .offset(y = (-18).dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue
                ),
                elevation = ButtonDefaults.buttonElevation(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.upload_photo).uppercase(),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ProfilePicTitle() {
    Text(
        text = stringResource(id = R.string.pfp),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun UpdateDaySelection(profileViewModel: ProfileViewModel) {
    val expanded by profileViewModel.expanded.collectAsState(initial = false)
    val selectedDays by profileViewModel.selectedDays.collectAsState()
    val userProfileData by profileViewModel.userProfileData.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.days),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.select_more_days),
            color = Color.Gray,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier.fillMaxWidth()
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
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (userProfileData.selectedDays.isEmpty()) {
                                stringResource(id = R.string.select_day)
                            } else {
                                userProfileData.selectedDays.joinToString(", ")
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
                            val daysOfWeek = listOf(
                                stringResource(id = R.string.monday),
                                stringResource(id = R.string.tuesday),
                                stringResource(id = R.string.wednesday),
                                stringResource(id = R.string.thursday),
                                stringResource(id = R.string.friday),
                                stringResource(id = R.string.saturday),
                                stringResource(id = R.string.sunday)
                            )

                            daysOfWeek.forEach { day ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            profileViewModel.onDayChecked(
                                                day,
                                                !selectedDays.contains(day)
                                            )
                                        }
                                        .padding(
                                            horizontal = 16.dp,
                                            vertical = 8.dp
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = selectedDays.contains(day),
                                        onCheckedChange = { checked ->
                                            profileViewModel.onDayChecked(
                                                day,
                                                checked
                                            )
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = day)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}