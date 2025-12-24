package dev.dwak.lender.feature.auth.ui

import dev.dwak.lender.app.navigation.core.LenderRoute

sealed interface AuthRoutes {
  data object Login : LenderRoute
  data object SignUp : LenderRoute
}