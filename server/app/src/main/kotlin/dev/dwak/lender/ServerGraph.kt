package dev.dwak.lender

import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.lender.repos.server.UserRepo
import dev.dwak.lender.server.common.AuthenticatedLenderRoute
import dev.dwak.lender.server.common.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@DependencyGraph(
  scope = AppScope::class,
)
interface ServerGraph {

  val apiRoutes: Set<LenderRoute>
  val authenticatedApiRoutes: Set<AuthenticatedLenderRoute>

  val apiKeyPlugin: ApiKeyPlugin

  val userRepo: UserRepo

  @Provides
  @Io
  fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

}
