package dev.dwak.lender.app.navigation.core

import kotlinx.serialization.Serializable

interface LoggedOutRoutes : LenderRoute{

  @Serializable
  data object Launch: LoggedOutRoutes

}