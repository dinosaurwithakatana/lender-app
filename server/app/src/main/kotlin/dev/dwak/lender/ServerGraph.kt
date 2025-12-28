package dev.dwak.lender

import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.lender.server.common.ApiRoutes
import dev.dwak.lender.server.common.AuthenticatedApiRoutes
import dev.dwak.lender.server.common.LenderRoute
import dev.dwak.lender.repos.server.UserRepo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@DependencyGraph(
  scope = AppScope::class,
)
interface ServerGraph {

  @ApiRoutes
  val apiRoutes: Set<LenderRoute>

  @AuthenticatedApiRoutes
  val authenticatedApiRoutes: Set<LenderRoute>

  val apiKeyPlugin: ApiKeyPlugin

  val userRepo: UserRepo

  @Provides
  @Io
  fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

}