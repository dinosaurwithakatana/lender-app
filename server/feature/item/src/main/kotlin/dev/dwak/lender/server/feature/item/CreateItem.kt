package dev.dwak.lender.server.feature.item

import dev.dwak.lender.data.modification.item.CreateItem
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.api.request.item.ApiCreateItem
import dev.dwak.lender.models.api.response.ApiCreateItemResponse
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
class CreateItem(
  private val profileRepo: ProfileRepo,
  private val dataModifier: DataModifier,
) : AuthenticatedLenderRoute {
  override val method: HttpMethod = HttpMethod.Post
  override val path: String = "/item"

  override fun handler(principal: UserIdToken): RoutingHandler = {
    val req = call.receive<ApiCreateItem>()
    val profileId = profileRepo.getByUserId(principal.userId).id

    when (val result = dataModifier.submit(
      CreateItem(
        name = req.name,
        description = req.description,
        quantity = req.quantity,
        ownedBy = profileId
      )
    )) {
      is CreateItem.Result.Success -> {
        call.respond(HttpStatusCode.OK, ApiCreateItemResponse(result.id.id))
      }
    }
  }
}