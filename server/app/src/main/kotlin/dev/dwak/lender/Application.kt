package dev.dwak.lender

import dev.dwak.lender.lender_app.Greeting
import dev.dwak.lender.lender_app.SERVER_PORT
import dev.dwak.lender.models.server.ServerToken
import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.server.common.AuthenticatedLenderRoute
import dev.dwak.lender.server.common.LenderRoute
import dev.zacsweers.metro.createGraph
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.bearer
import io.ktor.server.auth.principal
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json

fun main() {
  val graph = createGraph<ServerGraph>()
  Napier.base(DebugAntilog())
  embeddedServer(
    factory = Netty,
    port = SERVER_PORT,
    host = "0.0.0.0",
    module = {
      module(graph)
    }
  ).start(wait = true)
}

fun Application.module(graph: ServerGraph) {
  install(ContentNegotiation) {
    json(Json {
      allowTrailingComma = true
    })
  }

  install(Authentication) {
    bearer("bearer") {
      authenticate {
        if (graph.userRepo.tokenExists(it.token)) {
          UserIdToken(
            userId = graph.userRepo.getUserByToken(it.token).id,
            token = ServerToken(it.token)
          )
        } else {
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

fun Route.apiRoutes(routes: Set<LenderRoute>) {
  routes.forEach { route ->
    route(
      path = route.path,
      method = route.method,
      build = { handle(route.handler()) },
    )
  }
}

fun Route.authenticatedApiRoutes(routes: Set<AuthenticatedLenderRoute>) {
  routes.forEach { route ->
    route(
      path = route.path,
      method = route.method,
      build = {
        handle {
          route.handler(call.principal<UserIdToken>()!!)(this)
        }
      },
    )
  }
}
