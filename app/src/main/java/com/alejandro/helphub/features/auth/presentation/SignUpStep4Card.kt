package com.alejandro.helphub.features.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.alejandro.helphub.R
import com.alejandro.helphub.ui.theme.Black

@Preview(showBackground = true)
@Composable
fun SignUpStep4Card() {
    val listState = rememberLazyListState()
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
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(0f)
            ) {
                RegisterHeader()
                StepFourProgressIndicator()
                StepFourFinish()
                Spacer(modifier = Modifier.height(16.dp))
                CongratulationsBox()
                StepButtons(onBackClick = { }) {
                    
                }

            }
        }
    }
}

@Composable
fun CongratulationsBox() {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .background(Color.LightGray)
    ) {

        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Mis habilidades",
                fontSize = 28.sp,
                modifier = Modifier.padding(start = 30.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                color = Black,
                modifier = Modifier.padding(start = 30.dp, end = 30.dp)
            )
            Text(text = "¡Felicitationes! Ya tienes tu primera habilidad asociada a tu cuenta. Puedes editarla o borarla en cualquier momento desde tu perfil.",
                fontSize = 18.sp,
                modifier = Modifier.padding(
                    start = 30.dp,
                    end = 30.dp,
                    top = 12.dp
                )
            )
        }


    }
}

@Composable
fun StepFourFinish() {
    Text(
        text = stringResource(id = R.string.step_4),
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = stringResource(id = R.string.congratulations),
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.primary,
    )
}
