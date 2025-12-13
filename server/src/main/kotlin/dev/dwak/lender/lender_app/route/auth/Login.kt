package dev.dwak.lender.lender_app.route.auth

import dev.dwak.lender.db.UserQueries
import dev.dwak.lender.lender_app.ApiKeyRepo
import dev.dwak.lender.lender_app.models.api.auth.ApiLoginRequest
import dev.dwak.lender.lender_app.route.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.ClassKey
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.RoutingContext
import io.ktor.server.routing.method
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlin.reflect.KClass

fun Route.loginRoutes(@LoginRoutes routes: Set<LenderRoute>) {
    routes.forEach { route ->
        route(
            path = route.path,
            method = route.method,
            build = { handle(route.handler()) },
        )
    }
}

@LoginRoutes
@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
@Inject
class Login(
    private val userQueries: UserQueries
) : LenderRoute {
    override val method: HttpMethod = HttpMethod.Post
    override val path: String = "/login"

    override fun handler(): suspend RoutingContext.() -> Unit = {
        val loginRequest = call.receive<ApiLoginRequest>()
        call.respondText("You got it")
    }
}
