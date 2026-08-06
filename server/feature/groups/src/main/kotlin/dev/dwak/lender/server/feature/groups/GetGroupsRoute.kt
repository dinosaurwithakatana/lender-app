package dev.dwak.lender.server.feature.groups

import dev.dwak.lender.models.api.response.ApiGetGroupsResponse
import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.repos.server.GroupsRepo
import dev.dwak.lender.repos.server.ProfileRepo
import dev.dwak.lender.server.common.AuthenticatedLenderRoute
import dev.dwak.lender.server.common.toApiGroup
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
class GetGroupsRoute(
  private val groupsRepo: GroupsRepo,
  private val profileRepo: ProfileRepo
) : AuthenticatedLenderRoute {
  override val method: HttpMethod = HttpMethod.Get
  override val path: String = "/groups"

  context(call: ApplicationCall)
  override suspend fun handle(principal: UserIdToken) {
    val groups = groupsRepo.groupsForProfile(profileRepo.getByUserId(principal.userId)!!.id)
      .map {
        it.toApiGroup()
      }

    call.respond(ApiGetGroupsResponse(groups))
  }
}