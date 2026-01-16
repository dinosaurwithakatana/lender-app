package dev.dwak.lender.server.feature.groups

import dev.dwak.lender.data.modification.group.CreateGroupMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.api.request.group.ApiCreateGroupRequest
import dev.dwak.lender.models.api.response.ApiCreateGroupResponse
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
class CreateGroupRoute(
  private val dataModifier: DataModifier,
  private val profileRepo: ProfileRepo,
) : AuthenticatedTypedLenderRoute<ApiCreateGroupRequest> {
  override val method: HttpMethod = HttpMethod.Post
  override val path: String = "/groups"

  override val requestType: TypeInfo = typeInfo<ApiCreateGroupRequest>()

  context(call: ApplicationCall)
  override suspend fun handle(request: ApiCreateGroupRequest, principal: UserIdToken) {
    when (
      val result = dataModifier.submit(
        CreateGroupMod(
          name = request.name,
          owner = profileRepo.getByUserId(principal.userId).id
        )
      )) {
      is CreateGroupMod.Result.Success -> {
        call.respond(HttpStatusCode.OK, ApiCreateGroupResponse(result.groupId.id))
      }
    }
  }
}