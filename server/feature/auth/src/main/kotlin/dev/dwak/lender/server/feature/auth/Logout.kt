package dev.dwak.lender.server.feature.auth

import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.data.modification.auth.LogoutUser
import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.server.common.AuthenticatedLenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingHandler

@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
@Inject
class Logout(
  private val dataModifier: DataModifier,
) : AuthenticatedLenderRoute {
  override val method: HttpMethod
    get() = HttpMethod.Post
  override val path: String
    get() = "/logout"

  override fun handler(principal: UserIdToken): RoutingHandler = {
    when (
      dataModifier.submit(
        LogoutUser(
          token = principal.token
        )
      )) {
      LogoutUser.Result.Success -> {
        call.respond(HttpStatusCode.OK)
      }
    }
  }
}