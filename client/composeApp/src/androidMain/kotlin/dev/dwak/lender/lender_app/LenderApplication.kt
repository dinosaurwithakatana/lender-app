package dev.dwak.lender.lender_app

import android.app.Application
import dev.zacsweers.metro.createGraphFactory
import dev.zacsweers.metrox.android.MetroAppComponentProviders
import dev.zacsweers.metrox.android.MetroApplication

class LenderApplication : Application(), MetroApplication{
  override val appComponentProviders: MetroAppComponentProviders by lazy {
    createGraphFactory<AndroidClientGraph.Factory>().create(this)
  }
}