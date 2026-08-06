package dev.dwak.lender.app.network

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class RealServerConfigValidator : ServerConfigValidator {
  override suspend fun validate(serverUrl: String, apiKey: String): ServerValidationResult {
    val url = try {
      URLBuilder().takeFrom(serverUrl.trimEnd('/'))
        .appendPathSegments("api", "server-info")
        .buildString()
    } catch (e: Throwable) {
      return ServerValidationResult.Unreachable(e.message ?: "Invalid server URL")
    }

    val client = HttpClient {
      expectSuccess = false
      install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true; isLenient = true })
      }
    }
    return try {
      val response: HttpResponse = client.get(url) {
        header("X-Api-Key", apiKey)
      }
      when (response.status) {
        HttpStatusCode.OK -> ServerValidationResult.Valid
        HttpStatusCode.Unauthorized -> ServerValidationResult.InvalidApiKey
        else -> ServerValidationResult.Unreachable("Unexpected response: ${response.status}")
      }
    } catch (e: Throwable) {
      ServerValidationResult.Unreachable(e.message ?: "Failed to reach server")
    } finally {
      client.close()
    }
  }
}
