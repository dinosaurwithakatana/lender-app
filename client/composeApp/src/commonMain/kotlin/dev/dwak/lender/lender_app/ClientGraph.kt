package dev.dwak.lender.lender_app

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.dwak.lender.app.navigation.core.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph

@DependencyGraph(AppScope::class)
interface ClientGraph {
  val entryBuilders: Set<EntryProviderScope<LenderRoute>.() -> Unit>
}