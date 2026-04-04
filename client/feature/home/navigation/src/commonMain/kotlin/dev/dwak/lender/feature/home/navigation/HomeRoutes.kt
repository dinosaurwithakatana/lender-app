package dev.dwak.lender.feature.home.navigation

import dev.dwak.lender.app.navigation.AuthenticatedLenderRoute
import dev.dwak.lender.lender_app.Parcelize
import kotlinx.serialization.Serializable

sealed interface HomeRoutes : AuthenticatedLenderRoute {
  @Parcelize
  @Serializable
  data object Home: HomeRoutes
}