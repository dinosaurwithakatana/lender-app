package dev.dwak.lender.server.feature.auth

import dev.dwak.lender.data.modification.auth.LoginUser
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.api.request.auth.ApiLoginRequest
import dev.dwak.lender.models.api.response.ApiLoginSuccessResponse
import dev.dwak.lender.server.common.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingHandler

@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
@Inject
class Login(
  private val dataModifier: DataModifier,
) : LenderRoute {
  override val method: HttpMethod = HttpMethod.Post
  override val path: String = "/login"

  override fun handler(): RoutingHandler = {
    val loginRequest = call.receive<ApiLoginRequest>()
    when (
      val result = dataModifier.submit(
        LoginUser(
          email = loginRequest.email,
          password = loginRequest.password
        )
      )) {
      is LoginUser.Result.Failure -> {
        call.respond(HttpStatusCode.Unauthorized, result.error)
      }

      is LoginUser.Result.Success -> {
        call.respond(HttpStatusCode.OK, ApiLoginSuccessResponse(result.token))
      }
    }

  }
}