package dev.dwak.lender.server.feature.membership

import dev.dwak.lender.data.modification.group.AddProfileToGroupMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.api.request.membership.ApiCreateMembershipRequest
import dev.dwak.lender.models.api.response.ApiCreateMembershipResponse
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerGroupMembershipStatus
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
class CreateMembershipRoute(
  private val dataModifier: DataModifier,
  private val profileRepo: ProfileRepo,
) : AuthenticatedTypedLenderRoute<ApiCreateMembershipRequest> {
  override val method: HttpMethod = HttpMethod.Post
  override val path: String = "/memberships"

  override val requestType: TypeInfo = typeInfo<ApiCreateMembershipRequest>()

  context(call: ApplicationCall)
  override suspend fun handle(request: ApiCreateMembershipRequest, principal: UserIdToken) {
    val profileId = profileRepo.getByUserId(principal.userId)?.id
      ?: return call.respond(HttpStatusCode.NotFound, "Profile not found")

    when (
      val result = dataModifier.submit(
        AddProfileToGroupMod(
          profileId = profileId,
          groupId = ServerGroupId(request.groupId),
          status = ServerGroupMembershipStatus.REQUESTED,
        )
      )
    ) {
      is AddProfileToGroupMod.Result.Success -> {
        call.respond(
          HttpStatusCode.Accepted,
          ApiCreateMembershipResponse(id = result.membershipId.id),
        )
      }
    }
  }
}
