package dev.dwak.lender.feature.auth.presenter

import androidx.compose.runtime.Composable


class LoginPresenter {
  @Composable
  fun present(): LoginState {
    return LoginState() { event ->
      when (event) {
        LoginEvents.Login -> TODO()
      }
    }
  }
}