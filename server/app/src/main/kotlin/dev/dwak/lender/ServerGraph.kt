package dev.dwak.lender

import dev.dwak.lender.lender_app.AppDir
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.lender.repos.server.ApiKeyRepo
import dev.dwak.lender.repos.server.UserRepo
import dev.dwak.lender.server.common.AuthenticatedLenderRoute
import dev.dwak.lender.server.common.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.io.files.FileSystem
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@DependencyGraph(
  scope = AppScope::class,
)
interface ServerGraph {

  val apiRoutes: Set<LenderRoute>

  val apiKeyRepo: ApiKeyRepo

  val userRepo: UserRepo

  @Provides
  @SingleIn(AppScope::class)
  @Io
  fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

  @Provides
  @SingleIn(AppScope::class)
  @AppDir
  fun dataDirectory(): Path {
    val userHome = System.getProperty("user.home")
    val localStorageDir = Path(userHome, ".config/lender/server")
    if(!SystemFileSystem.exists(localStorageDir)) {
      SystemFileSystem.createDirectories(localStorageDir, true)
    }
    return localStorageDir
  }
}
