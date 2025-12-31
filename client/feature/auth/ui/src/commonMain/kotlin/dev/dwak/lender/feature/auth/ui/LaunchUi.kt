package dev.dwak.lender.feature.auth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LaunchUi(
  navigateToLogin: () -> Unit,
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Button(onClick = {
      navigateToLogin()
    }) {
      Text("Login")
    }
    Button(onClick = {

    }) {
      Text("Sign Up")
    }
  }
}