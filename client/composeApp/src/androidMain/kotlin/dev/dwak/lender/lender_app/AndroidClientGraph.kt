package dev.dwak.lender.lender_app

import android.app.Application
import android.content.Context
import dev.dwak.lender.lender_app.coroutines.Io
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metrox.android.MetroAppComponentProviders
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.io.files.Path
import okio.Path.Companion.toPath

@DependencyGraph(AppScope::class)
interface AndroidClientGraph : ClientGraph, MetroAppComponentProviders {
  @Provides
  fun provideApplicationContext(application: Application): Context = application

  @Provides
  @Io
  fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

  @Provides
  @Io
  fun ioScope(@Io dispatcher: CoroutineDispatcher): CoroutineScope = CoroutineScope(dispatcher)


  @Provides
  @AppDir
  fun appDir(context: Context) : Path {
    return Path(context.dataDir.path)
  }

  @DependencyGraph.Factory
  fun interface Factory {
    fun create(@Provides application: Application): AndroidClientGraph
  }
}