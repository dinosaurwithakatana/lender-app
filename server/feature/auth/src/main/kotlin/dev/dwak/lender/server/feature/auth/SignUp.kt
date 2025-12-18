package dev.dwak.lender.server.feature.auth

import dev.dwak.lender.data.modifier.CreateUser
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.lender_app.models.api.auth.ApiSignUpRequest
import dev.dwak.lender.lender_app.models.api.auth.ApiSignupSuccessResponse
import dev.dwak.lender.server.common.ApiRoutes
import dev.dwak.lender.server.common.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext

@ApiRoutes
@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
@Inject
class SignUp(
    private val dataModifier: DataModifier,
) : LenderRoute {
    override val method: HttpMethod = HttpMethod.Post
    override val path: String = "/signup"

    override fun handler(): suspend RoutingContext.() -> Unit = {
        val request = call.receive<ApiSignUpRequest>()

        if (request.password == request.confirmPassword) {
            val result = dataModifier.submit(
                CreateUser(
                    email = request.email,
                    password = request.password,
                    firstName = request.firstName,
                    lastName = request.lastName,
                )
            )
            when (result) {
                is CreateUser.Result.Success -> {
                    call.respond(HttpStatusCode.OK, ApiSignupSuccessResponse(result.token))
                }

                CreateUser.Result.InvalidEmail -> TODO()
                CreateUser.Result.InvalidPassword -> TODO()
            }
        }
    }
}