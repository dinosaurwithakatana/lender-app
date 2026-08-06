package dev.dwak.lender.server.feature.membership

import dev.dwak.lender.models.api.request.membership.ApiMembershipStatus
import dev.dwak.lender.models.api.response.ApiGetMembershipsResponse
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerGroupMembershipStatus
import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.repos.server.GroupMembershipRepo
import dev.dwak.lender.repos.server.ProfileRepo
import dev.dwak.lender.server.common.AuthenticatedLenderRoute
import dev.dwak.lender.server.common.toApi
import dev.dwak.lender.server.common.toApiMembership
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
class GetMembershipsRoute(
  private val groupMembershipRepo: GroupMembershipRepo,
  private val profileRepo: ProfileRepo,
) : AuthenticatedLenderRoute {
  override val method: HttpMethod = HttpMethod.Get
  override val path: String = "/memberships"

  context(call: ApplicationCall)
  override suspend fun handle(principal: UserIdToken) {
    val groupIdParam = call.request.queryParameters["groupId"]
      ?: return call.respond(HttpStatusCode.BadRequest, "Missing groupId")
    val group = ServerGroupId(groupIdParam)

    val callerProfileId = profileRepo.getByUserId(principal.userId)?.id
      ?: return call.respond(HttpStatusCode.NotFound, "Profile not found")

    if (!groupMembershipRepo.isProfileInGroup(callerProfileId, group)) {
      return call.respond(HttpStatusCode.Forbidden)
    }
    val callerIsOwner = groupMembershipRepo.isOwnerForGroup(callerProfileId, group)

    val statusFilter = call.request.queryParameters["status"]?.let { raw ->
      runCatching { ApiMembershipStatus.valueOf(raw) }.getOrNull()
        ?: return call.respond(HttpStatusCode.BadRequest, "Invalid status")
    }
    if (statusFilter == ApiMembershipStatus.REQUESTED && !callerIsOwner) {
      return call.respond(HttpStatusCode.Forbidden)
    }

    val memberships = groupMembershipRepo.membershipsForGroup(group)
      .filter { entry ->
        val visible = callerIsOwner || entry.membership.status != ServerGroupMembershipStatus.REQUESTED
        val matchesFilter = statusFilter == null || entry.membership.status.toApi() == statusFilter
        visible && matchesFilter
      }
      .map { entry ->
        entry.toApiMembership()
      }

    call.respond(ApiGetMembershipsResponse(memberships))
  }
}

