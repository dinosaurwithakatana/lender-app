package dev.dwak.lender.repos.client

import dev.dwak.lender.app.network.GroupsApi
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.models.client.ClientGroup
import dev.dwak.models.client.ClientProfile
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

@ContributesBinding(scope = AppScope::class, binding = binding<GroupsRepo>())
@ContributesBinding(
  scope = AppScope::class,
  binding = binding<RepoRefresher<GroupsRepo.RefreshTypes>>()
)
@SingleIn(AppScope::class)
class RealGroupsRepo(
  private val groupsApi: GroupsApi,
  @Io private val dispatcher: CoroutineDispatcher,
) : GroupsRepo, RepoRefresher<GroupsRepo.RefreshTypes> {
  override val currentUserGroups: Flow<List<ClientGroup>>
    field = MutableStateFlow(emptyList())

  override suspend fun refresh(item: GroupsRepo.RefreshTypes) = withContext(dispatcher){
    when (item) {
      GroupsRepo.RefreshTypes.CurrentUserGroups ->{
        currentUserGroups.value = groupsApi.getGroupsForCurrentUser()
          .groups
          .map {
            ClientGroup(
              id = ClientGroup.Id(it.id),
              name = it.name
            )
          }
      }
    }
  }

  override suspend fun getMembers(groupId: ClientGroup.Id): List<ClientProfile> {
    val response = groupsApi.getGroupMembers(groupId.id)
    return if (response.isSuccessful) {
      response.body()?.members?.map {
        ClientProfile(
          id = ClientProfile.Id(it.id),
          firstName = it.firstName,
          lastName = it.lastName,
        )
      } ?: emptyList()
    } else {
      emptyList()
    }
  }
}