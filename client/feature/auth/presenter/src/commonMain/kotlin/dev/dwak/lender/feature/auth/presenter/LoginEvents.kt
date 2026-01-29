package dev.dwak.lender.feature.auth.presenter

sealed interface LoginEvents {
  data object Login: LoginEvents
}