package dev.dwak.lender.lender_app.route

import dev.dwak.lender.lender_app.repo.UserRepo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respondText
import io.ktor.server.routing.RoutingContext

@AuthenticatedApiRoutes
@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
@Inject
class BearerTest(
    private val userRepo: UserRepo,
): LenderRoute {
    override val method: HttpMethod
        get() = HttpMethod.Get
    override val path: String
        get() = "/test-auth"

    override fun handler(): suspend RoutingContext.() -> Unit = {
        val userId = call.principal<UserIdPrincipal>()?.name
        val user = userRepo.getUserById(userId!!)
        call.respondText("Hello ${user.email}!")
    }
}