package dev.dwak.lender.app.navigation

import kotlinx.serialization.Serializable

interface LoggedOutRoutes : LenderRoute{

  @Serializable
  data object Launch: LoggedOutRoutes

}