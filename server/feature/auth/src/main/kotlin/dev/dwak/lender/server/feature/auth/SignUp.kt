package dev.dwak.lender.server.feature.auth

import dev.dwak.lender.data.modification.auth.CreateUser
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.api.request.auth.ApiSignUpRequest
import dev.dwak.lender.models.api.response.ApiSignupSuccessResponse
import dev.dwak.lender.server.common.LenderRoute
import dev.dwak.lender.server.common.TypedLenderRoute
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
class SignUp(
  private val dataModifier: DataModifier,
) : TypedLenderRoute<ApiSignUpRequest> {
  override val method: HttpMethod = HttpMethod.Post
  override val path: String = "/signup"
  override val requestType: TypeInfo
    get() = typeInfo<ApiSignUpRequest>()

  context(call: ApplicationCall)
  override suspend fun handle(request: ApiSignUpRequest) {
    if (request.password == request.confirmPassword) {
      val result = dataModifier.submit(
        CreateUser(
          email = request.email,
          password = request.password,
          firstName = request.firstName,
          lastName = request.lastName,
          inviteLinkToken = request.inviteLinkToken,
        )
      )
      when (result) {
        is CreateUser.Result.Success -> {
          call.respond(HttpStatusCode.OK, ApiSignupSuccessResponse(result.token))
        }

        CreateUser.Result.InvalidEmail -> TODO()
        CreateUser.Result.InvalidPassword -> TODO()
        CreateUser.Result.InvalidInviteLink -> {
          call.respond(HttpStatusCode.Unauthorized, "Invalid invite")
        }
      }
    }
  }
}