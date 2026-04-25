package dev.dwak.lender

import dev.dwak.lender.lender_app.Greeting
import dev.dwak.lender.lender_app.SERVER_PORT
import dev.dwak.lender.models.server.ServerToken
import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.data.modification.auth.CreateApiKeyMod
import dev.dwak.lender.server.common.AuthenticatedLenderRoute
import dev.dwak.lender.server.common.AuthenticatedTypedLenderRoute
import dev.dwak.lender.server.common.LenderRoute
import dev.zacsweers.metro.createGraph
import dev.zacsweers.metro.createGraphFactory
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.Napier
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.apikey.apiKey
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.bearer
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.staticResources
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import io.ktor.util.logging.KtorSimpleLogger
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.slf4j.helpers.BasicMarker
import org.slf4j.helpers.BasicMarkerFactory

fun main() {
  embeddedServer(
    factory = Netty,
    port = SERVER_PORT,
    host = "0.0.0.0",
    module = {
      val graph = createGraphFactory<ServerGraph.Factory>().create(this)
      Napier.base(graph.antilog)
      graph.application.module(graph)
    }
  ).start(wait = true)
}

fun Application.module(graph: ServerGraph) {
  install(ContentNegotiation) {
    json(Json {
      allowTrailingComma = true
    })
  }

  install(CORS) {
    anyHost()
    allowHeader(HttpHeaders.AccessControlAllowOrigin)
    allowHeader(HttpHeaders.ContentType)
    allowHeader("X-Api-Key")
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
    apiKey("api-key") {
      validate { apiKey ->
        val validKey = graph.apiKeyRepo.hasKey(apiKey)
        if (validKey) {
          ApiKeyPrincipal(apiKey)
        } else {
          null
        }
      }
      challenge {
        it.respond(HttpStatusCode.Unauthorized, "Valid API key required")
      }
    }
  }

  val webClientKey = runBlocking {
    graph.apiKeyRepo.getKeyByName("web-client")
      ?: (graph.dataModifier.submit(CreateApiKeyMod(name = "web-client")) as CreateApiKeyMod.Result.Success).apiKey.key
  }

  routing {
    get("/") {
      val html = this::class.java.classLoader
        .getResourceAsStream("static/index.html")!!
        .bufferedReader()
        .readText()
        .replace("\"__API_KEY__\"", "\"$webClientKey\"")
      call.respondText(html, ContentType.Text.Html)
    }

    staticResources("/", "static")

    authenticate("api-key", strategy = AuthenticationStrategy.Required) {
      route("/api") {
        apiRoutes(graph.apiRoutes.filterNot {
          it is AuthenticatedLenderRoute || it is AuthenticatedTypedLenderRoute<*>
        }.toSet())


        authenticate("bearer", strategy = AuthenticationStrategy.Required) {
          apiRoutes(graph.apiRoutes.filter {
            it is AuthenticatedLenderRoute || it is AuthenticatedTypedLenderRoute<*>
          }.toSet())
        }
      }
    }
  }
}

fun Route.apiRoutes(routes: Set<LenderRoute>) {
  routes.forEach { route ->
    route(
      path = route.path,
      method = route.method,
      build = {
        handle {
          with(this.call) {
            route.routeHandler()
          }
        }
      },
    )
  }
}
