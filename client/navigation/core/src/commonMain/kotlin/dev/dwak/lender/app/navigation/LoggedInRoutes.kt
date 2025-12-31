package dev.dwak.lender.app.navigation

import kotlinx.serialization.Serializable

interface LoggedInRoutes : AuthenticatedLenderRoute {
  @Serializable
  data object Home: LoggedInRoutes
}