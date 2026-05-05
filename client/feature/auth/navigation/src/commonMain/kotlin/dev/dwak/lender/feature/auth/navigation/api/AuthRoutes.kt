package dev.dwak.lender.feature.auth.navigation.api

import dev.dwak.lender.app.navigation.LenderScreen
import dev.dwak.lender.lender_app.Parcelize
import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthRoutes : LenderScreen {
  @Parcelize
  @Serializable
  data object Launch : AuthRoutes

  @Serializable
  @Parcelize
  data object Login : AuthRoutes

  @Parcelize
  @Serializable
  data object SignUp : AuthRoutes
}