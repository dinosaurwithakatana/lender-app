package dev.dwak.lender.server.feature.item

import dev.dwak.lender.data.modification.item.CreateItemMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.api.request.item.ApiCreateItem
import dev.dwak.lender.models.api.response.ApiCreateItemResponse
import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.repos.server.ProfileRepo
import dev.dwak.lender.server.common.AuthenticatedTypedLenderRoute
import dev.dwak.lender.server.common.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo

@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
class CreateItemRoute(
  private val profileRepo: ProfileRepo,
  private val dataModifier: DataModifier,
) : AuthenticatedTypedLenderRoute<ApiCreateItem> {
  override val method: HttpMethod = HttpMethod.Post
  override val path: String = "/item"
  override val requestType: TypeInfo = typeInfo<Unit>()

  context(call: ApplicationCall)
  override suspend fun handle(request: ApiCreateItem, principal: UserIdToken) {
    val profileId = profileRepo.getByUserId(principal.userId)?.id

    if (profileId == null) {
      call.respond(HttpStatusCode.NotFound, "Profile not found")
      return
    }

    when (val result = dataModifier.submit(
      CreateItemMod(
        name = request.name,
        description = request.description,
        quantity = request.quantity,
        ownedBy = profileId
      )
    )) {
      is CreateItemMod.Result.Success -> {
        call.respond(HttpStatusCode.OK, ApiCreateItemResponse(result.id.id))
      }
    }
  }
}