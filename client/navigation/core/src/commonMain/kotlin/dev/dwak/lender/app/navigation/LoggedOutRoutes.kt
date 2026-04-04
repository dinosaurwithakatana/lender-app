package dev.dwak.lender.app.navigation

import dev.dwak.lender.lender_app.Parcelize
import kotlinx.serialization.Serializable

interface LoggedOutRoutes : LenderRoute{

  @Parcelize
  @Serializable
  data object Launch: LoggedOutRoutes

}