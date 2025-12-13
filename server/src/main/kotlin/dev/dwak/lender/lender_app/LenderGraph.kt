package dev.dwak.lender.lender_app

import dev.dwak.lender.db.DatabaseProviders
import dev.dwak.lender.lender_app.route.LenderRoute
import dev.dwak.lender.lender_app.route.auth.LoginRoutes
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import kotlin.reflect.KClass

@DependencyGraph(
    scope = AppScope::class,
)
interface LenderGraph {

    @LoginRoutes
    val loginRoutes: Set<LenderRoute>

    val apiKeyPlugin: ApiKeyPlugin

}