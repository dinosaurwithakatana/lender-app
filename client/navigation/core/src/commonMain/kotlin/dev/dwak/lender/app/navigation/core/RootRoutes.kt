package dev.dwak.lender.app.navigation.core

import kotlinx.serialization.Serializable

interface RootRoutes : LenderRoute {

  @Serializable
  data object LoggedIn: RootRoutes

  @Serializable
  data object LoggedOut: RootRoutes
}