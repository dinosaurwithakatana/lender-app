package dev.dwak.lender.lender_app

import dev.dwak.lender.lender_app.route.ApiRoutes
import dev.dwak.lender.lender_app.route.AuthenticatedApiRoutes
import dev.dwak.lender.lender_app.route.LenderRoute
import dev.zacsweers.metro.createGraph
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.bearer
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun main() {
    val graph = createGraph<LenderGraph>()
    embeddedServer(
        factory = Netty,
        port = SERVER_PORT,
        host = "0.0.0.0",
        module = {
            module(graph)
        }
    ).start(wait = true)
}

fun Application.module(graph: LenderGraph) {
    install(ContentNegotiation) {
        json(Json {
            allowTrailingComma = true
        })
    }

    install(Authentication) {
        bearer("bearer") {
            authenticate {
                if (graph.userRepo.tokenExists(it.token)) {
                    UserIdPrincipal(graph.userRepo.getUserByToken(it.token).id)
                }
                else {
                    null
                }
            }
        }
    }

    routing {
        route("/api") {
            install(graph.apiKeyPlugin.plugin) { headerName = "X-API-Key" }
            apiRoutes(graph.apiRoutes)

            authenticate("bearer") {
                authenticatedApiRoutes(graph.authenticatedApiRoutes)
            }
        }
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
    }
}

fun Route.apiRoutes(@ApiRoutes routes: Set<LenderRoute>) {
    routes.forEach { route ->
        route(
            path = route.path,
            method = route.method,
            build = { handle(route.handler()) },
        )
    }
}

fun Route.authenticatedApiRoutes(@AuthenticatedApiRoutes routes: Set<LenderRoute>) {
    routes.forEach { route ->
        route(
            path = route.path,
            method = route.method,
            build = { handle(route.handler()) },
        )
    }
}
