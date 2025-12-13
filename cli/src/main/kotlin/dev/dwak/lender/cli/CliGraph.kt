package dev.dwak.lender.cli

import dev.dwak.lender.db.ApiKeyQueries
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph

@DependencyGraph(AppScope::class)
interface CliGraph {
    val apiKeyQueries: ApiKeyQueries
}