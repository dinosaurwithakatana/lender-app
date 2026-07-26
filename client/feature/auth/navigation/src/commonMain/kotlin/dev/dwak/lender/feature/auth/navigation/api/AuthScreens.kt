package dev.dwak.lender.feature.auth.navigation.api

import dev.dwak.lender.app.navigation.LenderScreen
import dev.dwak.lender.lender_app.Parcelize
import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthScreens : LenderScreen {
  @Parcelize
  @Serializable
  data object ConnectServer : AuthScreens

  @Parcelize
  @Serializable
  data object Launch : AuthScreens

  @Serializable
  @Parcelize
  data object Login : AuthScreens

  @Parcelize
  @Serializable
  data object SignUp : AuthScreens
}
