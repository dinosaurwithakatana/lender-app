package dev.dwak.lender.app.navigation

import kotlinx.serialization.Serializable

interface RootRoutes : LenderRoute {

  @Serializable
  data object LoggedIn: RootRoutes

  @Serializable
  data object LoggedOut: RootRoutes
}