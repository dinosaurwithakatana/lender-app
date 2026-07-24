package dev.dwak.lender.feature.lend.navigation

import com.slack.circuit.runtime.screen.PopResult
import dev.dwak.lender.app.navigation.AuthenticatedLenderScreen
import dev.dwak.lender.lender_app.Parcelize
import kotlinx.serialization.Serializable

@Serializable
sealed interface LendScreens : AuthenticatedLenderScreen {
  @Serializable
  @Parcelize
  data object LendHome : LendScreens

  @Serializable
  @Parcelize
  data object CreateLend : LendScreens {
    @Serializable
    @Parcelize
    data object LendCreatedResult : PopResult
  }
}
