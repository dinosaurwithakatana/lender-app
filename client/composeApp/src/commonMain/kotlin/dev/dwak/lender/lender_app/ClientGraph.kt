package dev.dwak.lender.lender_app

import androidx.navigation3.runtime.EntryProviderScope
import dev.dwak.lender.app.navigation.core.LenderRoute

interface ClientGraph {
  val entryBuilders: Set<EntryProviderScope<LenderRoute>.() -> Unit>
}