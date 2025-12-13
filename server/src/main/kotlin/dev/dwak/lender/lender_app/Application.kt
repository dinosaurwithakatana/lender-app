package dev.dwak.lender.lender_app

import dev.dwak.lender.lender_app.route.auth.login
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.createGraph
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.response.*
import io.ktor.server.routing.*

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
        json()
    }

    repos(graph)
    routing {
        route("/api") {
            val repo: ApiKeyRepo by dependencies
            val apiKeyPlugin = ApiKeyPlugin(apiKeyRepo = repo)
            install(apiKeyPlugin.plugin) { headerName = "X-API-Key" }
            login()
        }
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
    }
}

fun Application.repos(graph: LenderGraph) {
    dependencies {
        provide<ApiKeyRepo> { graph.apiKeyRepo }
    }
}