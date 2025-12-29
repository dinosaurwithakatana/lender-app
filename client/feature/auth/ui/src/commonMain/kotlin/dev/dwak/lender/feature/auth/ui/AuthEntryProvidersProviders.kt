package dev.dwak.lender.feature.auth.ui

import androidx.navigation3.runtime.EntryProviderScope
import dev.dwak.lender.app.navigation.core.LenderRoute
import dev.dwak.lender.feature.auth.ui.login.LoginUi
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.IntoSet
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

@ContributesTo(AppScope::class)
interface AuthEntryProvidersProviders {
  @Provides
  @IntoSet
  @SingleIn(AppScope::class)
  fun entryProviders(): EntryProviderScope<LenderRoute>.() -> Unit = {
    entry<AuthRoutes.Launch> {
      LaunchUi()
    }
    
    entry<AuthRoutes.Login> {
      LoginUi()
    }
  }
}