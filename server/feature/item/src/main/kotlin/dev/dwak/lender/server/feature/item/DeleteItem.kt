package dev.dwak.lender.server.feature.item

import dev.dwak.lender.data.modification.item.DeleteItem
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.api.request.item.ApiDeleteItem
import dev.dwak.lender.models.server.ServerItemId
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
import io.ktor.server.routing.RoutingHandler

@AuthenticatedApiRoutes
@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
class DeleteItem(
  private val profileRepo: ProfileRepo,
  private val dataModifier: DataModifier,
) : LenderRoute {
  override val method: HttpMethod = HttpMethod.Delete
  override val path: String = "/item"

  override fun handler(): RoutingHandler = {
    val principal = call.principal<UserIdToken>()!!
    val req = call.receive<ApiDeleteItem>()
    val profileId = profileRepo.getByUserId(principal.userId).id
    when (dataModifier.submit(
      DeleteItem(
        id = ServerItemId(req.id),
        ownedBy = profileId
      )
    )) {
      DeleteItem.Result.Failure -> call.respond(HttpStatusCode.NotFound)
      DeleteItem.Result.Success -> call.respond(HttpStatusCode.OK)
      DeleteItem.Result.Unauthorized -> call.respond(HttpStatusCode.Unauthorized)
    }
  }
}