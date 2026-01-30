package dev.dwak.lender.server.feature.groups

import dev.dwak.lender.data.modification.group.DeleteGroupMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.api.request.group.ApiDeleteGroupRequest
import dev.dwak.lender.models.server.ServerGroupId
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
class DeleteGroupRoute(
  private val dataModifier: DataModifier,
  private val profileRepo: ProfileRepo,
) : AuthenticatedTypedLenderRoute<ApiDeleteGroupRequest> {
  override val method: HttpMethod = HttpMethod.Delete
  override val path: String = "/groups"

  override val requestType: TypeInfo
    get() = typeInfo<ApiDeleteGroupRequest>()

  context(call: ApplicationCall)
  override suspend fun handle(request: ApiDeleteGroupRequest, principal: UserIdToken) {
    val profile = profileRepo.getByUserId(principal.userId)
    when (
      val result = dataModifier.submit(
        DeleteGroupMod(
          id = ServerGroupId(request.id),
          requestingProfileId = profile!!.id
        )
      )
    ) {
      DeleteGroupMod.Result.Success -> call.respond(HttpStatusCode.OK)
      DeleteGroupMod.Result.Unauthorized -> call.respond(HttpStatusCode.Unauthorized)
    }
  }
}