package dev.dwak.lender.server.feature.item

import dev.dwak.lender.data.modification.CreateItem
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.api.request.item.ApiCreateItem
import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.repos.server.ProfileRepo
import dev.dwak.lender.server.common.AuthenticatedApiRoutes
import dev.dwak.lender.server.common.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.routing.RoutingHandler

@AuthenticatedApiRoutes
@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
class CreateItem(
  private val profileRepo: ProfileRepo,
  private val dataModifier: DataModifier,
) : LenderRoute {
  override val method: HttpMethod = HttpMethod.Post
  override val path: String = "/item"

  override fun handler(): RoutingHandler = {
    val principal = call.principal<UserIdToken>()!!
    val req = call.receive<ApiCreateItem>()

    val profileId = profileRepo.getByUserId(principal.userId).id
    dataModifier.submit(
      CreateItem(
        name = req.name,
        description = req.description,
        quantity = req.quantity,
        ownedBy = profileId
      )
    )
  }
}