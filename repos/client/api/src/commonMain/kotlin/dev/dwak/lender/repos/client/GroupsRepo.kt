package dev.dwak.lender.repos.client

import dev.dwak.models.client.ClientGroup
import kotlinx.coroutines.flow.Flow

interface GroupsRepo {
  val currentUserGroups: Flow<List<ClientGroup>>

  sealed interface RefreshTypes: RepoRefresher.RefreshType {
    data object CurrentUserGroups: RefreshTypes
  }
}