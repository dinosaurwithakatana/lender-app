package dev.dwak.lender.feature.item.navigation

import dev.dwak.lender.app.navigation.AuthenticatedLenderScreen
import dev.dwak.lender.lender_app.Parcelize
import kotlinx.serialization.Serializable

sealed interface ItemScreens : AuthenticatedLenderScreen {
  @Parcelize
  @Serializable
  data object CreateItem: ItemScreens
}