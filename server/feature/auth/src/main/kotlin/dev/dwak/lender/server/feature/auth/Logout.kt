package dev.dwak.lender.server.feature.auth

import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.data.modification.auth.LogoutUser
import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.server.common.AuthenticatedLenderRoute
import dev.dwak.lender.server.common.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingHandler
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo

@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class, binding = binding<LenderRoute>())
class Logout(
  private val dataModifier: DataModifier,
) : AuthenticatedLenderRoute {
  override val method: HttpMethod
    get() = HttpMethod.Post
  override val path: String
    get() = "/logout"

  context(call: ApplicationCall)
  override suspend fun handle(principal: UserIdToken) {
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