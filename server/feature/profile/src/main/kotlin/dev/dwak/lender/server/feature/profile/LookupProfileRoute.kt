package dev.dwak.lender.server.feature.profile

import dev.dwak.lender.models.api.response.ApiProfile
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
class LookupProfileRoute(
  private val profileRepo: ProfileRepo,
) : AuthenticatedLenderRoute {
  override val method: HttpMethod = HttpMethod.Get
  override val path: String = "/profiles/lookup"

  context(call: ApplicationCall)
  override suspend fun handle(principal: UserIdToken) {
    val email = call.request.queryParameters["email"]
      ?: return call.respond(HttpStatusCode.BadRequest, "Missing email")

    val profile = profileRepo.getByEmail(email)
      ?: return call.respond(HttpStatusCode.NotFound)

    call.respond(
      ApiProfile(
        id = profile.id.id,
        firstName = profile.firstName,
        lastName = profile.lastName,
      )
    )
  }
}
