package dev.dwak.lender.lender_app.route.auth

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
    private val userQueries: UserQueries,
    private val tokenQueries: TokenQueries,
) : LenderRoute {
    override val method: HttpMethod = HttpMethod.Post
    override val path: String = "/signup"

    override fun handler(): suspend RoutingContext.() -> Unit = {
        val request = call.receive<ApiSignUpRequest>()

        if (request.password == request.confirmPassword) {
            val dbUser = DbUser(
                id = UUID.randomUUID().toString(),
                email = request.email,
                password = request.password,
                created_at = Clock.System.now().toString(),
            )
            userQueries.insert(
                dbUser = dbUser
            )
            val token = generateToken()

            tokenQueries.insertToken(
                DbToken(
                    token = token,
                    user_id = dbUser.id
                )
            )

            call.respond(HttpStatusCode.OK, ApiSignupSuccessResponse(token))
        }
    }
}