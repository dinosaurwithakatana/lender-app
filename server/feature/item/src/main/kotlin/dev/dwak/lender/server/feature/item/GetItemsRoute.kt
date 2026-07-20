package dev.dwak.lender.server.feature.item

import dev.dwak.lender.models.api.response.ApiItem
import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.repos.server.ItemRepo
import dev.dwak.lender.repos.server.ProfileRepo
import dev.dwak.lender.server.common.AuthenticatedLenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
class GetItemsRoute(
  private val itemRepo: ItemRepo,
  private val profileRepo: ProfileRepo,
): AuthenticatedLenderRoute {
  override val method: HttpMethod = HttpMethod.Get
  override val path: String = "/item"

  context(call: ApplicationCall)
  override suspend fun handle(principal: UserIdToken) {
    call.respond(
      itemRepo.getItemsForProfile(profileRepo.getByUserId(principal.userId)!!.id)
        .map {
          ApiItem(
            id = it.id.id,
            name = it.name,
            description = it.description,
            quantity = it.quantity,
            ownedById = it.ownedBy.id
          )
        }
    )
  }
}