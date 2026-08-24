package dev.dwak.lender.feature.groups.navigation

import com.slack.circuit.runtime.screen.PopResult
import dev.dwak.lender.app.navigation.AuthenticatedLenderScreen
import dev.dwak.lender.lender_app.Parcelize
import kotlinx.serialization.Serializable

@Serializable
sealed interface GroupsScreens : AuthenticatedLenderScreen {
  @Serializable
  @Parcelize
  data object GroupsHome : GroupsScreens

  @Serializable
  @Parcelize
  data object CreateGroup : GroupsScreens {
    @Serializable
    @Parcelize
    data object GroupCreatedResult : PopResult
  }

  @Serializable
  @Parcelize
  data class GroupDetail(val groupId: String) : GroupsScreens

  @Serializable
  @Parcelize
  data class AddMember(val groupId: String) : GroupsScreens {
    @Serializable
    @Parcelize
    data object MemberInvitedResult : PopResult
  }
}
