package com.alejandro.helphub.features.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController


@Composable
fun Home(navController:NavHostController){
    var isPopUpVisible by remember{ mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Button(onClick = { isPopUpVisible=true }) {
            Text(text = "Show Profile Popup")
            
        }
        UserProfilePopup(isPopupVisible=isPopUpVisible){
            isPopUpVisible=false
            navController.navigate("ProfileSetupStep1")
        }
    }
}

@Composable
fun UserProfilePopup(isPopupVisible: Boolean, onDismiss: () -> Unit) {
    if (isPopupVisible) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = onDismiss
        ) {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "User Profile")
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("This is the user profile popup content.")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onDismiss) {

                        Text("Close")
                    }
                }
            }
        }
    }
}
