package dev.dwak.lender

import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.lender_app.AppDir
import dev.dwak.lender.lender_app.DbDir
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.lender.repos.server.ApiKeyRepo
import dev.dwak.lender.repos.server.UserRepo
import dev.dwak.lender.server.common.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.LogLevel
import io.ktor.server.application.Application
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import org.slf4j.IMarkerFactory
import org.slf4j.helpers.BasicMarkerFactory

@DependencyGraph(
  scope = AppScope::class,
)
interface ServerGraph {
  val application: Application

  val apiRoutes: Set<LenderRoute>

  val apiKeyRepo: ApiKeyRepo

  val dataModifier: DataModifier

  val userRepo: UserRepo

  val antilog: Antilog

  @Provides
  @SingleIn(AppScope::class)
  @Io
  fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

  @Provides
  @SingleIn(AppScope::class)
  @AppDir
  fun appDirectory(): Path {
    val base = System.getenv("LENDER_DATA_DIR") ?: "./build/run"
    val dir = Path("$base/server")
    if (!SystemFileSystem.exists(dir)) {
      SystemFileSystem.createDirectories(dir, true)
    }
    return dir
  }

  @Provides
  @SingleIn(AppScope::class)
  @DbDir
  fun databaseDirectory(): Path {
    val base = System.getenv("LENDER_DATA_DIR") ?: "./build/run"
    val dir = Path("$base/data")
    if (!SystemFileSystem.exists(dir)) {
      SystemFileSystem.createDirectories(dir, true)
    }
    return dir
  }

  @Provides
  @SingleIn(AppScope::class)
  fun markerFactory(): IMarkerFactory = BasicMarkerFactory()

  @DependencyGraph.Factory
  fun interface Factory {
    fun create(@Provides application: Application): ServerGraph
  }
}
