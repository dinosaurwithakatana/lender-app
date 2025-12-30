package dev.dwak.lender.feature.auth.navigation.api

import dev.dwak.lender.app.navigation.core.LenderRoute
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthRoutes : LenderRoute {
  @Serializable
  data object Launch: AuthRoutes

  @Serializable
  data object Login : AuthRoutes

  @Serializable
  data object SignUp : AuthRoutes
}