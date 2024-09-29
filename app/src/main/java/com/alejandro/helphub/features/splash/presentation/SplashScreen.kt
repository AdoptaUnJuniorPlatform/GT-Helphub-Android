package com.alejandro.helphub.features.splash.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alejandro.helphub.R

@Preview
@Composable
fun SplashScreen() {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        //verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.weight(1f)) {
            Spacer(modifier = Modifier.fillMaxSize())
        }
        Box(modifier = Modifier) {
            Image(
                painter = painterResource(id = R.drawable.vector_logo_blanco),
                contentDescription = "logo Helphub",
                modifier = Modifier.size(160.dp)
            )
        }
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()) {
            Text(
                text = "HelpHub",
                modifier = Modifier
                    .align(Alignment.Center),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.background


            )
        }
    }
}