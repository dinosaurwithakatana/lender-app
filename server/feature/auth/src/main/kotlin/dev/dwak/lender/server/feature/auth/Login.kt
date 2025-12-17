package dev.dwak.lender.server.feature.auth

import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.data.modifier.LoginUser
import dev.dwak.lender.lender_app.models.api.auth.ApiLoginRequest
import dev.dwak.lender.lender_app.models.api.auth.ApiLoginSuccessResponse
import dev.dwak.lender.repos.server.UserRepo
import dev.dwak.lender.server.common.ApiRoutes
import dev.dwak.lender.server.common.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@ApiRoutes
@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
@Inject
class Login(
    private val userRepo: UserRepo,
    private val dataModifier: DataModifier,
) : LenderRoute {
    override val method: HttpMethod = HttpMethod.Post
    override val path: String = "/login"

    override fun handler(): suspend RoutingContext.() -> Unit = {
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