package dev.dwak.lender.feature.lend.navigation

import dev.dwak.lender.app.navigation.AuthenticatedLenderScreen
import dev.dwak.lender.lender_app.Parcelize
import kotlinx.serialization.Serializable

@Serializable
sealed interface LendScreens : AuthenticatedLenderScreen {
  @Serializable
  @Parcelize
  data object LendHome : LendScreens
}
