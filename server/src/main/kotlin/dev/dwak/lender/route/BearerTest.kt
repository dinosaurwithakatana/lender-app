package dev.dwak.lender.route

import dev.dwak.lender.repos.server.UserRepo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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