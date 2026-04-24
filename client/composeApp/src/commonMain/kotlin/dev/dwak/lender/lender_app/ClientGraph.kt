package dev.dwak.lender.lender_app

import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.ExperimentalCircuitApi
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuitx.navigation.intercepting.NavigationInterceptor
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

interface ClientGraph {
  val circuit: Circuit
  val navigationInterceptors: Set<NavigationInterceptor>

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