package dev.dwak.lender.app.navigation

import dev.dwak.lender.lender_app.Parcelize
import kotlinx.serialization.Serializable

interface LoggedOutRoutes : LenderScreen{

  @Parcelize
  @Serializable
  data object Launch: LoggedOutRoutes

}