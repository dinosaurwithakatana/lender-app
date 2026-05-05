package dev.dwak.lender.feature.home.navigation

import dev.dwak.lender.app.navigation.AuthenticatedLenderScreen
import dev.dwak.lender.lender_app.Parcelize
import kotlinx.serialization.Serializable

sealed interface HomeScreens : AuthenticatedLenderScreen {
  @Parcelize
  @Serializable
  data object Home: HomeScreens
}