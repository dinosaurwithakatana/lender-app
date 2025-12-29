package dev.dwak.lender.lender_app

import android.app.Application
import android.content.Context
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metrox.android.MetroAppComponentProviders

@DependencyGraph(AppScope::class)
interface AndroidClientGraph : ClientGraph, MetroAppComponentProviders {
  @Provides
  fun provideApplicationContext(application: Application): Context = application

  @DependencyGraph.Factory
  fun interface Factory {
    fun create(@Provides application: Application): AndroidClientGraph
  }
}