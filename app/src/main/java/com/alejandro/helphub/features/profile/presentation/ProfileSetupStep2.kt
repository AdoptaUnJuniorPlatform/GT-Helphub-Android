package com.alejandro.helphub.features.profile.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.alejandro.helphub.R

@Composable
fun ProfileSetupStep2(
    profileViewModel: ProfileViewModel,
    navController: NavHostController
) {
    var showDialog by remember { mutableStateOf(false) }
    val isStep3Enabled by profileViewModel.isNavigationToStep3Enabled.collectAsState(
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
                .blur(if (showDialog) 12.dp else 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(0f)
            ) {
                RegisterHeader()
                StepTwoProgressIndicator()
                StepTwoTitle()
                Spacer(modifier = Modifier.height(20.dp))
                ChooseImage(showDialog = showDialog,
                    onShowDialogChange = { showDialog = it })
                Spacer(modifier = Modifier.height(20.dp))
                UploadPhoto(profileViewModel)
                Examples()
                Spacer(modifier = Modifier.height(40.dp))
                StepButtons(
                    onBackClick = {
                        navController.navigate("ProfileSetupStep1")
                    },
                    onNextClick = {
                        navController.navigate("ProfileSetupStep3")
                    },
                    enabled = isStep3Enabled
                )
            }
        }
        ShowDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false })
    }
}

@Composable
fun ShowDialog(showDialog: Boolean, onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = stringResource(id = R.string.photo_dialog),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            },
            text = {
                Column {
                    Text(
                        text = stringResource(id = R.string.photo_first_indication_dialog),
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(id = R.string.photo_second_indication_dialog),
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(id = R.string.photo_third_indication_dialog),
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(id = R.string.photo_fourth_indication_dialog),
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(id = R.string.photo_fifth_indication_dialog),
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { onDismiss() },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue
                    )
                ) {
                    Text(text = stringResource(id = R.string.confirm_button))
                }
            },
            shape = RoundedCornerShape(6.dp),
            containerColor = Color.White
        )
    }
}

@Composable
fun UploadPhoto(profileViewModel: ProfileViewModel) {
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            photoUri = it
            profileViewModel.updateUserPhotoUri(it)
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
                    if (photoUri != null) {
                        rememberAsyncImagePainter(photoUri)
                    } else {
                        painterResource(id = R.drawable.default_profile_icon)
                    },
                    contentDescription = stringResource(id = R.string.user_photo_content_description),
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .then(
                            if (photoUri == null) Modifier.border(
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
fun Examples() {
    Column {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.profiles),
                contentDescription = stringResource(id = R.string.profile_examples),
                modifier = Modifier
                    .weight(1f)
                    .size(120.dp),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.example_photo),
                    fontSize = 16.sp, color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun ChooseImage(showDialog: Boolean, onShowDialogChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.choose_photo),
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(modifier = Modifier
            .padding(end = 12.dp)
            .align(Alignment.CenterVertically)
            .clickable { onShowDialogChange(true) }) {
            Row(horizontalArrangement = Arrangement.End) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = stringResource(
                        id = R.string.photo_dialog
                    ),
                    tint = Color.Blue,
                    modifier = Modifier
                        .size(20.dp)
                )
                Text(
                    text = stringResource(id = R.string.photo_advice).uppercase(),
                    color = Color.Blue,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = stringResource(id = R.string.step_two_description),
        color = Color.Gray, fontSize = 20.sp
    )
}

@Composable
fun StepTwoTitle() {
    Text(
        text = stringResource(id = R.string.step_2),
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = stringResource(id = R.string.step_2_title),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.primary,
    )
}

@Composable
fun StepTwoProgressIndicator() {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.step_two),
                contentDescription = stringResource(id = R.string.step_2),
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}