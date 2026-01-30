package dev.dwak.lender.feature.home.navigation

import dev.dwak.lender.app.navigation.AuthenticatedLenderRoute

sealed interface HomeRoutes : AuthenticatedLenderRoute {
  data object Home: HomeRoutes
}