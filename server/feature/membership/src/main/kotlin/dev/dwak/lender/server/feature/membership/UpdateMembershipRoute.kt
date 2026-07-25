package dev.dwak.lender.server.feature.membership

import dev.dwak.lender.data.modification.group.ApproveMembershipMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.api.request.membership.ApiMembershipStatus
import dev.dwak.lender.models.api.request.membership.ApiUpdateMembershipRequest
import dev.dwak.lender.models.server.ServerGroupMembershipId
import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.repos.server.ProfileRepo
import dev.dwak.lender.server.common.AuthenticatedTypedLenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo

@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
class UpdateMembershipRoute(
  private val dataModifier: DataModifier,
  private val profileRepo: ProfileRepo,
) : AuthenticatedTypedLenderRoute<ApiUpdateMembershipRequest> {
  override val method: HttpMethod = HttpMethod.Patch
  override val path: String = "/memberships/{id}"

  override val requestType: TypeInfo = typeInfo<ApiUpdateMembershipRequest>()

  context(call: ApplicationCall)
  override suspend fun handle(request: ApiUpdateMembershipRequest, principal: UserIdToken) {
    val id = call.parameters["id"]
      ?: return call.respond(HttpStatusCode.BadRequest, "Missing membership id")

    if (request.status != ApiMembershipStatus.APPROVED) {
      return call.respond(HttpStatusCode.BadRequest, "Only APPROVED is a valid update target")
    }

    val actingProfileId = profileRepo.getByUserId(principal.userId)?.id
      ?: return call.respond(HttpStatusCode.NotFound, "Profile not found")

    when (
      dataModifier.submit(
        ApproveMembershipMod(
          membershipId = ServerGroupMembershipId(id),
          actingProfileId = actingProfileId,
        )
      )
    ) {
      ApproveMembershipMod.Result.Success -> call.respond(HttpStatusCode.OK)
      ApproveMembershipMod.Result.NotFound -> call.respond(HttpStatusCode.NotFound)
      ApproveMembershipMod.Result.Unauthorized -> call.respond(HttpStatusCode.Forbidden)
    }
  }
}
