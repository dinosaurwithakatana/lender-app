package dev.dwak.lender.feature.profile.navigation

import dev.dwak.lender.app.navigation.AuthenticatedLenderScreen
import dev.dwak.lender.lender_app.Parcelize
import kotlinx.serialization.Serializable

@Serializable
sealed interface ProfileScreens : AuthenticatedLenderScreen {
  @Serializable
  @Parcelize
  data object ProfileHome : ProfileScreens
}
