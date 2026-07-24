package dev.dwak.lender.server.feature.groups

import dev.dwak.lender.models.api.response.ApiGetGroupsResponse
import dev.dwak.lender.models.api.response.ApiGroup
import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.repos.server.GroupsRepo
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
class ListGroupsRoute(
  private val groupsRepo: GroupsRepo,
  private val profileRepo: ProfileRepo
): AuthenticatedLenderRoute {
  override val method: HttpMethod = HttpMethod.Get
  override val path: String = "/groups/{id}"

  context(call: ApplicationCall)
  override suspend fun handle(principal: UserIdToken) {
    if (call.parameters["id"] == "me") {
      val groups = groupsRepo.groupsForProfile(profileRepo.getByUserId(principal.userId)!!.id)
        .map {
          ApiGroup(
            id = it.id.id,
            name = it.name,
            createdAt = it.createdAt.toString()
          )
        }

      call.respond(ApiGetGroupsResponse(groups))
    }
  }
}