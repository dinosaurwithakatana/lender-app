package dev.dwak.lender.server.feature.auth

import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.data.modifier.LogoutUser
import dev.dwak.lender.repos.server.UserRepo
import dev.dwak.lender.server.common.ApiRoutes
import dev.dwak.lender.server.common.AuthenticatedApiRoutes
import dev.dwak.lender.server.common.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.routing.RoutingContext

@AuthenticatedApiRoutes
@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
@Inject
class Logout(
    private val userRepo: UserRepo,
    private val dataModifier: DataModifier,
) : LenderRoute {
    override val method: HttpMethod
        get() = HttpMethod.Post
    override val path: String
        get() = "/logout"

    override fun handler(): suspend RoutingContext.() -> Unit = {
        val principal = call.principal<UserIdPrincipal>()
        val user = userRepo.getUserByToken(principal!!.name)

        when (val result = dataModifier.submit(LogoutUser(
            id = user.id
        ))) {

            else -> {}
        }
    }
}