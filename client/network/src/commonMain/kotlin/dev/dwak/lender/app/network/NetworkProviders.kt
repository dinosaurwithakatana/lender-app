package dev.dwak.lender.app.network

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.annotations
import de.jensklingenberg.ktorfit.converter.ResponseConverterFactory
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.aakira.napier.Napier
import io.ktor.client.plugins.api.ClientPlugin
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.URLBuilder
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val PLACEHOLDER_BASE_URL = "http://server.placeholder/api/"

@ContributesTo(AppScope::class)
interface NetworkProviders {
  @Provides
  @SingleIn(AppScope::class)
  fun authPlugin(authPluginConfig: () -> AuthPluginConfig): ClientPlugin<AuthPluginConfig> {
    return createClientPlugin(
      name = "AuthPlugin",
      createConfiguration = { authPluginConfig() }
    ) {
      onRequest { request, content ->
        val auth = request.annotations
          .filterIsInstance<AuthRequired>()
          .firstOrNull()
          ?: return@onRequest

        val token = this@createClientPlugin.pluginConfig.token()

        if (token != null) {
          request.headers.append("Authorization", "Bearer $token")
        } else {
          error("no token for auth!")
        }
      }
    }
  }

  @Provides
  @SingleIn(AppScope::class)
  fun serverHostPlugin(
    serverConfigProvider: () -> ServerConfigProvider,
  ): ClientPlugin<ServerConfigProvider> {
    return createClientPlugin(
      name = "ServerHostPlugin",
      createConfiguration = { serverConfigProvider() }
    ) {
      onRequest { request, _ ->
        val cfg = this@createClientPlugin.pluginConfig.current()
        val target = URLBuilder().takeFrom(cfg.serverUrl.trimEnd('/'))
        request.url.protocol = target.protocol
        request.url.host = target.host
        request.url.port = target.port
        request.headers["X-Api-Key"] ?: request.headers.append("X-Api-Key", cfg.apiKey)
      }
    }
  }

  @Provides
  @SingleIn(AppScope::class)
  fun ktorfit(
    authClientPlugin: ClientPlugin<AuthPluginConfig>,
    serverHostClientPlugin: ClientPlugin<ServerConfigProvider>,
  ): Ktorfit = Ktorfit.Builder()
    .baseUrl(PLACEHOLDER_BASE_URL)
    .httpClient {
      defaultRequest {
        contentType(ContentType.Application.Json)
      }
      install(ContentNegotiation) {
        json(Json {
          isLenient = true
          ignoreUnknownKeys = true
        })
      }

      install(serverHostClientPlugin)
      install(authClientPlugin)

      install(Logging) {
        level = LogLevel.ALL
        logger = object : Logger {
          override fun log(message: String) {
            Napier.d { message }
          }
        }
      }
    }
    .converterFactories(ResponseConverterFactory())
    .build()

  @Provides
  @SingleIn(AppScope::class)
  fun loginApi(ktorfit: Ktorfit): LoginApi = ktorfit.createLoginApi()

  @Provides
  @SingleIn(AppScope::class)
  fun itemApi(ktorfit: Ktorfit): ItemApi = ktorfit.createItemApi()

  @Provides
  @SingleIn(AppScope::class)
  fun groupApi(ktorfit: Ktorfit): GroupsApi = ktorfit.createGroupsApi()

  @Provides
  @SingleIn(AppScope::class)
  fun membershipsApi(ktorfit: Ktorfit): MembershipsApi = ktorfit.createMembershipsApi()

  @Provides
  @SingleIn(AppScope::class)
  fun lendApi(ktorfit: Ktorfit): LendApi = ktorfit.createLendApi()

  @Provides
  @SingleIn(AppScope::class)
  fun profileApi(ktorfit: Ktorfit): ProfileApi = ktorfit.createProfileApi()
}
