package dev.dwak.lender.feature.groups.navigation

import dev.dwak.lender.app.navigation.AuthenticatedLenderScreen
import dev.dwak.lender.lender_app.Parcelize
import kotlinx.serialization.Serializable

@Serializable
sealed interface GroupsScreens : AuthenticatedLenderScreen {
  @Serializable
  @Parcelize
  data object GroupsHome : GroupsScreens
}