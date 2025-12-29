package dev.dwak.lender.feature.auth.ui

import dev.dwak.lender.app.navigation.core.LenderRoute
import kotlinx.serialization.Serializable

sealed interface AuthRoutes : LenderRoute {
  @Serializable
  data object Launch: AuthRoutes

  @Serializable
  data object Login : AuthRoutes

  @Serializable
  data object SignUp : AuthRoutes
}