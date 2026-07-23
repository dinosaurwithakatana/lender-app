package dev.dwak.lender.feature.item.navigation

import com.slack.circuit.runtime.screen.PopResult
import dev.dwak.lender.app.navigation.AuthenticatedLenderScreen
import dev.dwak.lender.lender_app.Parcelize
import kotlinx.serialization.Serializable

@Serializable
sealed interface ItemScreens : AuthenticatedLenderScreen {
  @Parcelize
  @Serializable
  data object CreateItem: ItemScreens {
    @Parcelize
    @Serializable
    data object ItemCreatedResult: PopResult
  }
}