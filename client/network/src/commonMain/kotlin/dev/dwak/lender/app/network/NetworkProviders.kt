package dev.dwak.lender.app.network

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.ResponseConverterFactory
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
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
  fun ktorfit(
    @Named("base-url") baseUrl: String,
  ): Ktorfit = Ktorfit.Builder()
    .baseUrl(baseUrl)
    .httpClient {
      defaultRequest {
        contentType(ContentType.Application.Json)
        header("X-Api-Key", "f355dba1d5db4c7a938a369d6a125991")
      }
      install(ContentNegotiation) {
        json(Json {
          isLenient = true
          ignoreUnknownKeys = true
        })
      }
    }
    .converterFactories(ResponseConverterFactory())
    .build()

  @Provides
  @SingleIn(AppScope::class)
  fun loginApi(ktorfit: Ktorfit): LoginApi = ktorfit.createLoginApi()
}