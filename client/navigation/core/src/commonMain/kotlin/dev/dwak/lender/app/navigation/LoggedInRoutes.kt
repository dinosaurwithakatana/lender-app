package dev.dwak.lender.app.navigation

import dev.dwak.lender.lender_app.Parcelize
import kotlinx.serialization.Serializable

interface LoggedInRoutes : AuthenticatedLenderRoute {
  @Parcelize
  @Serializable
  data object Home: LoggedInRoutes
}