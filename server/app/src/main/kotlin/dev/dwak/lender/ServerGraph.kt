package dev.dwak.lender

import dev.dwak.lender.lender_app.AppDir
import dev.dwak.lender.lender_app.DbDir
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.repos.server.ApiKeyRepo
import dev.dwak.lender.repos.server.UserRepo
import dev.dwak.lender.server.common.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

@DependencyGraph(
  scope = AppScope::class,
)
interface ServerGraph {

  val apiRoutes: Set<LenderRoute>

  val apiKeyRepo: ApiKeyRepo

  val dataModifier: DataModifier

  val userRepo: UserRepo

  @Provides
  @SingleIn(AppScope::class)
  @Io
  fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

  @Provides
  @SingleIn(AppScope::class)
  @AppDir
  fun appDirectory(): Path {
    val localStorageDir = Path("/data", "/server")
    if(!SystemFileSystem.exists(localStorageDir)) {
      SystemFileSystem.createDirectories(localStorageDir, true)
    }
    return localStorageDir
  }

  @Provides
  @SingleIn(AppScope::class)
  @DbDir
  fun databaseDirectory(): Path {
    val localStorageDir = Path("/data", "/data")
    if(!SystemFileSystem.exists(localStorageDir)) {
      SystemFileSystem.createDirectories(localStorageDir, true)
    }
    return localStorageDir
  }
}
