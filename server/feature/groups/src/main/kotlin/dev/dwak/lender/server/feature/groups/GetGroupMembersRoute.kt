package dev.dwak.lender.server.feature.groups

import dev.dwak.lender.models.api.response.ApiGetGroupMembersResponse
import dev.dwak.lender.models.api.response.ApiProfile
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.repos.server.GroupMembershipRepo
import dev.dwak.lender.repos.server.ProfileRepo
import dev.dwak.lender.server.common.AuthenticatedLenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
class GetGroupMembersRoute(
  private val groupMembershipRepo: GroupMembershipRepo,
  private val profileRepo: ProfileRepo,
) : AuthenticatedLenderRoute {
  override val method: HttpMethod = HttpMethod.Get
  override val path: String = "/groups/{groupId}/members"

  context(call: ApplicationCall)
  override suspend fun handle(principal: UserIdToken) {
    val groupId = call.parameters["groupId"]
      ?: return call.respond(HttpStatusCode.BadRequest, "Missing groupId")

    val callerProfileId = profileRepo.getByUserId(principal.userId)?.id
      ?: return call.respond(HttpStatusCode.NotFound, "Profile not found")

    val serverGroupId = ServerGroupId(groupId)
    if (!groupMembershipRepo.isProfileInGroup(callerProfileId, serverGroupId)) {
      return call.respond(HttpStatusCode.Forbidden)
    }

    val members = groupMembershipRepo.profilesInGroup(serverGroupId).map {
      ApiProfile(
        id = it.id.id,
        firstName = it.firstName,
        lastName = it.lastName,
      )
    }
    call.respond(ApiGetGroupMembersResponse(members))
  }
}
