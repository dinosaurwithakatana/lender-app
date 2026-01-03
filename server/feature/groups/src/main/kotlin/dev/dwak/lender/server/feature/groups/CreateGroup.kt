package dev.dwak.lender.server.feature.groups

import dev.dwak.lender.data.modification.CreateGroup
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.api.request.group.ApiCreateGroupRequest
import dev.dwak.lender.models.api.response.ApiCreateGroupResponse
import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.repos.server.ProfileRepo
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
class CreateGroup(
  private val dataModifier: DataModifier,
  private val profileRepo: ProfileRepo,
) : LenderRoute {
  override val method: HttpMethod = HttpMethod.Post
  override val path: String = "/groups"

  override fun handler(): suspend RoutingContext.() -> Unit = {
    val req = call.receive<ApiCreateGroupRequest>()
    val principal = call.principal<UserIdToken>()!!

    when (
      val result = dataModifier.submit(
        CreateGroup(
          name = req.name,
          owner = profileRepo.getByUserId(principal.userId).id
        )
      )) {
      is CreateGroup.Result.Success -> {
        call.respond(HttpStatusCode.OK, ApiCreateGroupResponse(result.groupId.id))
      }
    }
  }
}