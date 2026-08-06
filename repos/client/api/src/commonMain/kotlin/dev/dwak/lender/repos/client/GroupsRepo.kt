package dev.dwak.lender.repos.client

import dev.dwak.models.client.ClientGroup
import dev.dwak.models.client.ClientGroupDetail
import dev.dwak.models.client.ClientProfile
import kotlinx.coroutines.flow.Flow

interface GroupsRepo {
  val currentUserGroups: Flow<List<ClientGroup>>

  fun groupDetail(groupId: ClientGroup.Id): Flow<ClientGroupDetail?>

  suspend fun getMembers(groupId: ClientGroup.Id): List<ClientProfile>

  sealed interface RefreshTypes : RepoRefresher.RefreshType {
    data object CurrentUserGroups : RefreshTypes
    data class GroupDetail(val groupId: ClientGroup.Id) : RefreshTypes
  }
}