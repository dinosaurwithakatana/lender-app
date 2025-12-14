package dev.dwak.lender.lender_app.route.auth

import dev.dwak.lender.data.modifier.CreateUser
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.db.DbToken
import dev.dwak.lender.db.DbUser
import dev.dwak.lender.db.TokenQueries
import dev.dwak.lender.db.UserQueries
import dev.dwak.lender.lender_app.generateToken
import dev.dwak.lender.lender_app.models.api.auth.ApiSignUpRequest
import dev.dwak.lender.lender_app.models.api.auth.ApiSignupSuccessResponse
import dev.dwak.lender.lender_app.route.ApiRoutes
import dev.dwak.lender.lender_app.route.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext
import java.util.UUID
import kotlin.time.Clock
import kotlin.time.Instant

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
                    request.email,
                    request.password
                )
            )
            when (result) {
                is CreateUser.Result.Success -> {
                    call.respond(HttpStatusCode.OK, ApiSignupSuccessResponse(result.token))
                }
            }
        }
    }
}