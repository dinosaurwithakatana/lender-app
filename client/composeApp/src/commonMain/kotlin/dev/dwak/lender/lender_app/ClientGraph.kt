package dev.dwak.lender.lender_app

import androidx.savedstate.serialization.SavedStateConfiguration
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.animation.AnimatedScreenTransform
import com.slack.circuit.runtime.ExperimentalCircuitApi
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.app.navigation.LenderRouteEntryProvider
import dev.dwak.lender.app.navigation.NavigationSerializers
import dev.dwak.lender.app.navigation.Navigator
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.serialization.modules.SerializersModule
import kotlin.reflect.KClass

interface ClientGraph {
  val circuit: Circuit

  val entryBuilders: Set<LenderRouteEntryProvider>

  val savedStateConfiguration: SavedStateConfiguration

  @OptIn(ExperimentalCircuitApi::class)
  @SingleIn(AppScope::class)
  @Provides
  fun provideCircuit(
    presenterFactories: Set<Presenter.Factory>,
    uiFactories: Set<Ui.Factory>,
//    animatedScreenTransforms: Map<KClass<out Screen>, AnimatedScreenTransform>,
  ): Circuit {
    return Circuit.Builder()
      .addPresenterFactories(presenterFactories)
      .addUiFactories(uiFactories)
//      .addAnimatedScreenTransforms(animatedScreenTransforms)
      .build()
  }
}