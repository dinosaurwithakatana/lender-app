package dev.dwak.lender.server.feature.membership

import dev.dwak.lender.data.modification.group.RemoveMembershipMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.server.ServerGroupMembershipId
import dev.dwak.lender.models.server.UserIdToken
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
class DeleteMembershipRoute(
  private val dataModifier: DataModifier,
  private val profileRepo: ProfileRepo,
) : AuthenticatedLenderRoute {
  override val method: HttpMethod = HttpMethod.Delete
  override val path: String = "/memberships/{id}"

  context(call: ApplicationCall)
  override suspend fun handle(principal: UserIdToken) {
    val id = call.parameters["id"]
      ?: return call.respond(HttpStatusCode.BadRequest, "Missing membership id")

    val actingProfileId = profileRepo.getByUserId(principal.userId)?.id
      ?: return call.respond(HttpStatusCode.NotFound, "Profile not found")

    when (
      dataModifier.submit(
        RemoveMembershipMod(
          membershipId = ServerGroupMembershipId(id),
          actingProfileId = actingProfileId,
        )
      )
    ) {
      RemoveMembershipMod.Result.Success -> call.respond(HttpStatusCode.OK)
      RemoveMembershipMod.Result.NotFound -> call.respond(HttpStatusCode.NotFound)
      RemoveMembershipMod.Result.Unauthorized -> call.respond(HttpStatusCode.Forbidden)
    }
  }
}
