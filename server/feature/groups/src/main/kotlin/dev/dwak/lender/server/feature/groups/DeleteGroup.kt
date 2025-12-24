package dev.dwak.lender.server.feature.groups

import dev.dwak.lender.data.modification.CreateGroup
import dev.dwak.lender.data.modification.DeleteGroup
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.api.auth.request.ApiCreateGroupRequest
import dev.dwak.lender.models.api.auth.request.ApiDeleteGroupRequest
import dev.dwak.lender.models.api.auth.response.ApiCreateGroupResponse
import dev.dwak.lender.models.server.ServerGroup
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.repos.server.ProfileRepo
import dev.dwak.lender.server.common.ApiRoutes
import dev.dwak.lender.server.common.AuthenticatedApiRoutes
import dev.dwak.lender.server.common.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext

@AuthenticatedApiRoutes
@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
class DeleteGroup(
  private val dataModifier: DataModifier,
  private val profileRepo: ProfileRepo,
) : LenderRoute {
  override val method: HttpMethod = HttpMethod.Delete
  override val path: String = "/groups"

  override fun handler(): suspend RoutingContext.() -> Unit = {
    val req = call.receive<ApiDeleteGroupRequest>()
    val principal = call.principal<UserIdToken>()!!

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