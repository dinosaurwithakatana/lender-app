package dev.dwak.lender.server.feature.groups

import dev.dwak.lender.models.api.response.ApiGetGroupDetailResponse
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerGroupMembershipWithProfile
import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.repos.server.GroupMembershipRepo
import dev.dwak.lender.repos.server.GroupsRepo
import dev.dwak.lender.server.common.AuthenticatedLenderRoute
import dev.dwak.lender.server.common.toApiGroup
import dev.dwak.lender.server.common.toApiMembership
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
class GetGroupDetailRoute(
  private val groupsRepo: GroupsRepo,
  private val membershipRepo: GroupMembershipRepo,
) : AuthenticatedLenderRoute {
  override val method: HttpMethod = HttpMethod.Get
  override val path: String = "/groups/{id}"

  context(call: ApplicationCall)
  override suspend fun handle(principal: UserIdToken) {
    val groupId = call.parameters["id"]!!
    val group = groupsRepo.groupById(id = ServerGroupId(groupId))!!
    val memberships = membershipRepo.membershipsForGroup(ServerGroupId(groupId))
      .map(ServerGroupMembershipWithProfile::toApiMembership)

    call.respond(
      ApiGetGroupDetailResponse(
        group = group.toApiGroup(),
        memberships = memberships
      )
    )
  }

}