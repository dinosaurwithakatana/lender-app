package dev.dwak.lender.feature.auth.presenter.signup

sealed interface SignUpEvents {
  data object SignUp: SignUpEvents
}