package dev.dwak.lender.lender_app

import dev.dwak.lender.lender_app.route.ApiRoutes
import dev.dwak.lender.lender_app.route.AuthenticatedApiRoutes
import dev.dwak.lender.lender_app.route.LenderRoute
import dev.dwak.lender.repos.server.UserRepo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph

@DependencyGraph(
    scope = AppScope::class,
)
interface LenderGraph {

    @ApiRoutes
    val apiRoutes: Set<LenderRoute>

    @AuthenticatedApiRoutes
    val authenticatedApiRoutes: Set<LenderRoute>

    val apiKeyPlugin: ApiKeyPlugin

    val userRepo: UserRepo

}