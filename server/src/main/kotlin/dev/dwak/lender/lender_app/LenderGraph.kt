package dev.dwak.lender.lender_app

import dev.dwak.lender.lender_app.route.LenderRoute
import dev.dwak.lender.lender_app.route.ApiRoutes
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph

@DependencyGraph(
    scope = AppScope::class,
)
interface LenderGraph {

    @ApiRoutes
    val apiRoutes: Set<LenderRoute>

    val apiKeyPlugin: ApiKeyPlugin

}