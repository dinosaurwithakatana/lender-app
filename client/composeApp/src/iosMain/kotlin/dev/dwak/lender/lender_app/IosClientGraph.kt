package dev.dwak.lender.lender_app

import dev.dwak.lender.lender_app.coroutines.Io
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.io.files.Path
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@DependencyGraph(AppScope::class)
interface IosClientGraph : ClientGraph {

  @Provides
  @Io
  fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

  @Provides
  @Io
  fun ioScope(@Io dispatcher: CoroutineDispatcher): CoroutineScope = CoroutineScope(dispatcher)

  @OptIn(ExperimentalForeignApi::class)
  @Provides
  @AppDir
  fun appDir(): Path {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
      directory = NSDocumentDirectory,
      inDomain = NSUserDomainMask,
      appropriateForURL = null,
      create = false,
      error = null,
    )
    return Path(requireNotNull(documentDirectory).path!!)
  }
}