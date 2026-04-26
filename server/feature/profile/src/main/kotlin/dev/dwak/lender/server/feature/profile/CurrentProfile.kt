package dev.dwak.lender.server.feature.profile

import dev.dwak.lender.models.api.response.ApiProfileResponse
import dev.dwak.lender.models.server.ServerProfileId
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
class CurrentProfile(
  private val profileRepo: ProfileRepo,
) : AuthenticatedLenderRoute {
  override val method: HttpMethod = HttpMethod.Get
  override val path: String = "/profiles/{profileId}"

  context(call: ApplicationCall)
  override suspend fun handle(principal: UserIdToken) {
    val profile = if (call.parameters["profileId"] == "me") {
      profileRepo.getByUserId(principal.userId)
    }
    else {
      profileRepo.getProfileById(ServerProfileId(call.parameters.get("profileId")!!))
    }

    if (profile != null) {
      call.respond(ApiProfileResponse(
        profileId = profile.id.id,
        firstName = profile.firstName,
        lastName = profile.lastName
      ))
    }
    else {
      call.respond(HttpStatusCode.NotFound)
    }
  }
}