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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.io.File

@DependencyGraph(
  scope = AppScope::class,
)
interface ServerGraph {

  val apiRoutes: Set<LenderRoute>

  val apiKeyRepo: ApiKeyRepo

  val userRepo: UserRepo

  @Provides
  @Io
  fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

  @Provides
  @AppDir
  fun dataDirectory(): File {
    val userHome = System.getProperty("user.home")
    val localStorageDir = File(userHome, ".config/lender-cli")
    if (!localStorageDir.exists()) {
      localStorageDir.mkdir();
    }
    return localStorageDir
  }
}
