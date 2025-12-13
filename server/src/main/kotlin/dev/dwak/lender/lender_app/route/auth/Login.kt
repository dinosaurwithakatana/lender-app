package dev.dwak.lender.lender_app.route.auth

import dev.dwak.lender.lender_app.ApiKeyRepo
import dev.dwak.lender.lender_app.models.api.auth.ApiLoginRequest
import dev.dwak.lender.lender_app.route.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.RoutingContext
import io.ktor.server.routing.method
import io.ktor.server.routing.post
import io.ktor.server.routing.route

context(application: Application)
fun Route.login() {
    val login: Login by application.dependencies
    route(
        login.path,
        login.method,
    ) { handle(login.handler()) }
}

@LoginRoutes
@SingleIn(AppScope::class)
@Inject
@ContributesIntoSet(AppScope::class)
class Login() : LenderRoute {
    override val method: HttpMethod = HttpMethod.Post
    override val path: String = "/login"
    override val parent: LenderRoute? = null

    override fun handler(): suspend RoutingContext.() -> Unit = {
    }
}
