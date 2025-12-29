package dev.dwak.lender.lender_app

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph

@DependencyGraph(AppScope::class)
interface WebClientGraph: ClientGraph {
}