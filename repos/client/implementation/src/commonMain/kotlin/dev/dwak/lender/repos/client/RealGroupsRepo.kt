package dev.dwak.lender.repos.client

import dev.dwak.lender.app.network.GroupsApi
import dev.dwak.lender.app.network.MembershipsApi
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.lender.models.api.request.membership.ApiMembershipStatus
import dev.dwak.lender.models.api.response.ApiMembership
import dev.dwak.models.client.ClientGroup
import dev.dwak.models.client.ClientGroupDetail
import dev.dwak.models.client.ClientMembership
import dev.dwak.models.client.ClientMembershipStatus
import dev.dwak.models.client.ClientProfile
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@ContributesBinding(scope = AppScope::class, binding = binding<GroupsRepo>())
@ContributesBinding(
  scope = AppScope::class,
  binding = binding<RepoRefresher<GroupsRepo.RefreshTypes>>()
)
@SingleIn(AppScope::class)
class RealGroupsRepo(
  private val groupsApi: GroupsApi,
  private val membershipsApi: MembershipsApi,
  @Io private val dispatcher: CoroutineDispatcher,
) : GroupsRepo, RepoRefresher<GroupsRepo.RefreshTypes> {
  override val currentUserGroups: Flow<List<ClientGroup>>
    field = MutableStateFlow(emptyList())

  private val groupDetails = MutableStateFlow<Map<ClientGroup.Id, ClientGroupDetail>>(emptyMap())

  override fun groupDetail(groupId: ClientGroup.Id): Flow<ClientGroupDetail?> =
    groupDetails.map { it[groupId] }

  override suspend fun refresh(item: GroupsRepo.RefreshTypes) = withContext(dispatcher) {
    when (item) {
      GroupsRepo.RefreshTypes.CurrentUserGroups -> {
        currentUserGroups.value = groupsApi.getGroupsForCurrentUser()
          .groups
          .map {
            ClientGroup(
              id = ClientGroup.Id(it.id),
              name = it.name
            )
          }
      }

      is GroupsRepo.RefreshTypes.GroupDetail -> {
        val response = groupsApi.getGroup(groupId = item.groupId.id)
        val detail = ClientGroupDetail(
          group = ClientGroup(
            id = ClientGroup.Id(response.group.id),
            name = response.group.name,
          ),
          memberships = response.memberships.map(ApiMembership::toClient),
        )
        groupDetails.value = groupDetails.value + (item.groupId to detail)
      }
    }
  }

  override suspend fun getMembers(groupId: ClientGroup.Id): List<ClientProfile> {
    val response = membershipsApi.getMemberships(groupId = groupId.id)
    return if (response.isSuccessful) {
      response.body()?.memberships?.map {
        ClientProfile(
          id = ClientProfile.Id(it.profile.id),
          firstName = it.profile.firstName,
          lastName = it.profile.lastName,
        )
      } ?: emptyList()
    } else {
      emptyList()
    }
  }
}

private fun ApiMembership.toClient(): ClientMembership = ClientMembership(
  id = ClientMembership.Id(id),
  groupId = ClientGroup.Id(groupId),
  status = status.toClient(),
  profile = ClientProfile(
    id = ClientProfile.Id(profile.id),
    firstName = profile.firstName,
    lastName = profile.lastName,
  ),
)

private fun ApiMembershipStatus.toClient(): ClientMembershipStatus = when (this) {
  ApiMembershipStatus.APPROVED -> ClientMembershipStatus.APPROVED
  ApiMembershipStatus.REQUESTED -> ClientMembershipStatus.REQUESTED
  ApiMembershipStatus.OWNER -> ClientMembershipStatus.OWNER
}