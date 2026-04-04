package dev.dwak.lender.app.navigation

import dev.dwak.lender.lender_app.Parcelize
import kotlinx.serialization.Serializable

interface RootRoutes : LenderRoute {

  @Parcelize
  @Serializable
  data object LoggedIn: RootRoutes

  @Parcelize
  @Serializable
  data object LoggedOut: RootRoutes
}