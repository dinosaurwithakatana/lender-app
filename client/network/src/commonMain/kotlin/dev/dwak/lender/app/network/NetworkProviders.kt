package dev.dwak.lender.app.network

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.annotations
import dev.dwak.lender.lender_app.getWebApiKey
import de.jensklingenberg.ktorfit.converter.ResponseConverterFactory
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.aakira.napier.Napier
import io.ktor.client.plugins.api.ClientPlugin
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@ContributesTo(AppScope::class)
interface NetworkProviders {
  @Provides
  @SingleIn(AppScope::class)
  @Named("base-url")
  fun baseUrl(): String = "http://localhost:8080/api/"

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
          error("no token for auth required!")
        }
      }
    }
  }

  @Provides
  @SingleIn(AppScope::class)
  fun ktorfit(
    @Named("base-url") baseUrl: String,
    clientPlugin: ClientPlugin<AuthPluginConfig>
  ): Ktorfit = Ktorfit.Builder()
    .baseUrl(baseUrl)
    .httpClient {
      defaultRequest {
        contentType(ContentType.Application.Json)
        header(HttpHeaders.AccessControlAllowOrigin, "*")
        header("X-Api-Key", getWebApiKey())
      }
      install(ContentNegotiation) {
        json(Json {
          isLenient = true
          ignoreUnknownKeys = true
        })
      }

      install(clientPlugin)

      install(Logging) {
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

}