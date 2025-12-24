package dev.dwak.lender.app.network

import de.jensklingenberg.ktorfit.Ktorfit
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

@ContributesTo(AppScope::class)
interface NetworkProviders {
  @Provides
  @SingleIn(AppScope::class)
  @Named("base-url")
  fun baseUrl(): String = "https://localhost:8080/api"

  @Provides
  @SingleIn(AppScope::class)
  fun ktorfit(
    @Named("base-url") baseUrl: String,
  ): Ktorfit = Ktorfit.Builder().baseUrl(baseUrl).build()

  @Provides
  @SingleIn(AppScope::class)
  fun loginApi(ktorfit: Ktorfit): LoginApi = ktorfit.createLoginApi()
}