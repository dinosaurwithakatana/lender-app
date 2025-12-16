package dev.dwak.lender.route.auth

import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.data.modifier.LoginUser
import dev.dwak.lender.lender_app.models.api.auth.ApiLoginRequest
import dev.dwak.lender.lender_app.models.api.auth.ApiLoginSuccessResponse
import dev.dwak.lender.route.ApiRoutes
import dev.dwak.lender.route.LenderRoute
import dev.dwak.lender.repos.server.UserRepo
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
class Login(
    private val userRepo: UserRepo,
    private val dataModifier: DataModifier,
) : LenderRoute {
    override val method: HttpMethod = HttpMethod.Post
    override val path: String = "/login"

    override fun handler(): suspend RoutingContext.() -> Unit = {
        val loginRequest = call.receive<ApiLoginRequest>()

        val userExists = userRepo.userExistsByEmail(loginRequest.email)

        if (userExists) {
            val user = userRepo.getUserByEmail(loginRequest.email)
            when (val result = dataModifier.submit(
                LoginUser(
                    serverUser = user
                )
            )) {
                is LoginUser.Result.Failure -> {

                }

                is LoginUser.Result.Success -> {
                    call.respond(HttpStatusCode.OK, ApiLoginSuccessResponse(result.token))
                }
            }

        } else {
            call.respond(HttpStatusCode.Forbidden, "User doesn't exist")
        }
    }
}
