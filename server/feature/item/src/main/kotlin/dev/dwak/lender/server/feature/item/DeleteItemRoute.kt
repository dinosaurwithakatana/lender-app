package dev.dwak.lender.server.feature.item

import dev.dwak.lender.data.modification.item.DeleteItemMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.server.ServerItemId
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
class DeleteItemRoute(
  private val profileRepo: ProfileRepo,
  private val dataModifier: DataModifier,
) : AuthenticatedLenderRoute {
  override val method: HttpMethod = HttpMethod.Delete
  override val path: String = "/items/{itemId}"

  context(call: ApplicationCall)
  override suspend fun handle(principal: UserIdToken) {
    val itemId = call.parameters["itemId"]
      ?: return call.respond(HttpStatusCode.BadRequest, "Missing itemId")

    val profileId = profileRepo.getByUserId(principal.userId)?.id
      ?: return call.respond(HttpStatusCode.NotFound)

    when (dataModifier.submit(
      DeleteItemMod(
        id = ServerItemId(itemId),
        ownedBy = profileId,
      )
    )) {
      DeleteItemMod.Result.Failure -> call.respond(HttpStatusCode.NotFound)
      DeleteItemMod.Result.Success -> call.respond(HttpStatusCode.OK)
      DeleteItemMod.Result.Unauthorized -> call.respond(HttpStatusCode.Unauthorized)
    }
  }
}
