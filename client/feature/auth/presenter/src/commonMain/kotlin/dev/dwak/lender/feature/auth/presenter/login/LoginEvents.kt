package dev.dwak.lender.feature.auth.presenter.login

sealed interface LoginEvents {
  data object Login: LoginEvents
}