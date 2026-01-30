package dev.dwak.lender.feature.auth.presenter.launch

sealed interface LaunchEvents {
  data object GoToLogin: LaunchEvents

  data object GoToSignUp: LaunchEvents
}