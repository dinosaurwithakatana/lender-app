package dev.dwak.lender.server.feature.auth

import dev.dwak.lender.data.modification.auth.LoginUserMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.api.request.auth.ApiLoginRequest
import dev.dwak.lender.models.api.response.ApiLoginSuccessResponse
import dev.dwak.lender.server.common.LenderRoute
import dev.dwak.lender.server.common.TypedLenderRoute
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
class LoginRoute(
  private val dataModifier: DataModifier,
) : TypedLenderRoute<ApiLoginRequest> {
  override val method: HttpMethod = HttpMethod.Post
  override val path: String = "/login"
  override val requestType: TypeInfo = typeInfo<ApiLoginRequest>()

  context(call: ApplicationCall)
  override suspend fun handle(request: ApiLoginRequest) {
    when (
      val result = dataModifier.submit(
        LoginUserMod(
          email = request.email,
          password = request.password
        )
      )) {
      is LoginUserMod.Result.Failure -> {
        call.respond(HttpStatusCode.Unauthorized, result.error)
      }

      is LoginUserMod.Result.Success -> {
        call.respond(HttpStatusCode.OK, ApiLoginSuccessResponse(result.token))
      }
    }
  }
}