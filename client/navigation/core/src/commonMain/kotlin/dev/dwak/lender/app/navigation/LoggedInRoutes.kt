package dev.dwak.lender.app.navigation

import dev.dwak.lender.lender_app.Parcelize
import kotlinx.serialization.Serializable

interface LoggedInRoutes : AuthenticatedLenderScreen {
  @Parcelize
  @Serializable
  data object Home: LoggedInRoutes
}