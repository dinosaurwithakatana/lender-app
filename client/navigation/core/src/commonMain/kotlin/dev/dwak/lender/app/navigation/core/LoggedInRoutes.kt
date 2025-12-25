package dev.dwak.lender.app.navigation.core

import kotlinx.serialization.Serializable

interface LoggedInRoutes : AuthenticatedLenderRoute {
  @Serializable
  data object Home: LoggedInRoutes
}