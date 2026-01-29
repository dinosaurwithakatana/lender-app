package dev.dwak.lender.navigation

import androidx.savedstate.serialization.SavedStateConfiguration
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.animation.AnimatedScreenTransform
import com.slack.circuit.runtime.ExperimentalCircuitApi
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.app.navigation.LenderRoute
import dev.dwak.lender.app.navigation.NavigationSerializers
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlin.reflect.KClass

@ContributesTo(AppScope::class)
interface NavigationProviders {

  @Provides
  @SingleIn(AppScope::class)
  fun navigationSerializationModule(@NavigationSerializers modules: Set<SerializersModule>): SerializersModule =
    SerializersModule {
      polymorphic(LenderRoute::class) {
        modules.forEach { include(it) }
      }
    }

  @Provides
  @SingleIn(AppScope::class)
  fun savedStateConfig(module: SerializersModule): SavedStateConfiguration =
    SavedStateConfiguration { serializersModule = module }

}