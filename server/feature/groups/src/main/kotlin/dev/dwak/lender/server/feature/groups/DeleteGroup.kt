package dev.dwak.lender.server.feature.groups

import dev.dwak.lender.data.modification.group.DeleteGroup
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.api.request.group.ApiDeleteGroupRequest
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.repos.server.ProfileRepo
import dev.dwak.lender.server.common.AuthenticatedLenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingHandler

@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
class DeleteGroup(
  private val dataModifier: DataModifier,
  private val profileRepo: ProfileRepo,
) : AuthenticatedLenderRoute {
  override val method: HttpMethod = HttpMethod.Delete
  override val path: String = "/groups"

  override fun handler(principal: UserIdToken): RoutingHandler = {
    val req = call.receive<ApiDeleteGroupRequest>()

    val profile = profileRepo.getByUserId(principal.userId)
    when (
      val result = dataModifier.submit(
        DeleteGroup(
          id = ServerGroupId(req.id),
          requestingProfileId = profile.id
        )
      )
    ) {
      DeleteGroup.Result.Success -> call.respond(HttpStatusCode.OK)
      DeleteGroup.Result.Unauthorized -> call.respond(HttpStatusCode.Unauthorized)
    }
  }
}