package dev.dwak.lender.feature.auth.navigation.api

import com.slack.circuit.runtime.screen.Screen
import dev.dwak.lender.app.navigation.LenderRoute
import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthRoutes : LenderRoute, Screen {
  @Serializable
  data object Launch: AuthRoutes

  @Serializable
  data object Login : AuthRoutes

  @Serializable
  data object SignUp : AuthRoutes
}