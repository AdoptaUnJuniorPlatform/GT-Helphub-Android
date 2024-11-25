package com.alejandro.helphub.presentation.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alejandro.helphub.R

@Composable
fun NotificationsScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column{
            Image(
                painter = painterResource(id = R.drawable.helphub_morado),
                contentDescription = "",
                modifier = Modifier
                    .size(200.dp)
                    .aspectRatio(16 / 9f)
                    .align(Alignment.CenterHorizontally)
            )
            Text(text = "Notifications", fontSize = 24.sp)
        }
    }
}