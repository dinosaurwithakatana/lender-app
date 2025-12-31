package dev.dwak.lender.feature.auth.ui

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.dwak.lender.app.navigation.core.LenderRoute
import dev.dwak.lender.app.navigation.core.LenderRouteEntryProvider
import dev.dwak.lender.app.navigation.core.NavigationSerializers
import dev.dwak.lender.feature.auth.navigation.api.AuthRoutes
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.IntoSet
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@ContributesTo(AppScope::class)
interface AuthEntryProvidersProviders {
  @Provides
  @IntoSet
  @SingleIn(AppScope::class)
  fun entryProviders(): LenderRouteEntryProvider = {
    entry<AuthRoutes.Launch> {
      LaunchUi(
        navigateToLogin = {

        }
      )
    }

    entry<AuthRoutes.Login> {
      LoginUi()
    }
  }

  @OptIn(ExperimentalSerializationApi::class)
  @Provides
  @SingleIn(AppScope::class)
  @IntoSet
  @NavigationSerializers
  fun serializersModule(): SerializersModule = SerializersModule {
    polymorphic(LenderRoute::class) {
      subclassesOfSealed<AuthRoutes>()
    }
  }
}