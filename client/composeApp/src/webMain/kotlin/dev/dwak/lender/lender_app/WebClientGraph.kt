package dev.dwak.lender.lender_app

import dev.dwak.lender.lender_app.coroutines.Io
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.io.files.Path

@DependencyGraph(AppScope::class)
interface WebClientGraph: ClientGraph {
  @Provides
  @Io
  fun ioDispatcher(): CoroutineDispatcher = Dispatchers.Default

  @Provides
  @Io
  fun ioScope(@Io dispatcher: CoroutineDispatcher): CoroutineScope = CoroutineScope((dispatcher))

  @Provides
  @AppDir
  fun applicationDir(): Path = Path("")
}