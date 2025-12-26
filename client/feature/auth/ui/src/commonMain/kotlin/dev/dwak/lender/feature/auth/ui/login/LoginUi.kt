package dev.dwak.lender.feature.auth.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

@Composable
fun LoginUi() {
  Column {
    val username = rememberTextFieldState()
    val password = rememberTextFieldState()
    Text("Username")
    TextField(username)
    Text("Password")
    TextField(password)
  }
}