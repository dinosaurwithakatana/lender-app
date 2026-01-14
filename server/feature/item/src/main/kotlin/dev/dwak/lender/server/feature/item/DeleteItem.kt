package dev.dwak.lender.server.feature.item

import dev.dwak.lender.data.modification.item.DeleteItem
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.api.request.item.ApiDeleteItem
import dev.dwak.lender.models.server.ServerItemId
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
@ContributesIntoSet(AppScope::class, binding = binding<LenderRoute>())
class DeleteItem(
  private val profileRepo: ProfileRepo,
  private val dataModifier: DataModifier,
) : AuthenticatedTypedLenderRoute<ApiDeleteItem> {
  override val method: HttpMethod = HttpMethod.Delete
  override val path: String = "/item"
  override val requestType: TypeInfo
    get() = typeInfo<ApiDeleteItem>()

  context(call: ApplicationCall)
  override suspend fun handle(request: ApiDeleteItem, principal: UserIdToken) {
    val profileId = profileRepo.getByUserId(principal.userId).id
    when (dataModifier.submit(
      DeleteItem(
        id = ServerItemId(request.id),
        ownedBy = profileId
      )
    )) {
      DeleteItem.Result.Failure -> call.respond(HttpStatusCode.NotFound)
      DeleteItem.Result.Success -> call.respond(HttpStatusCode.OK)
      DeleteItem.Result.Unauthorized -> call.respond(HttpStatusCode.Unauthorized)
    }
  }
}