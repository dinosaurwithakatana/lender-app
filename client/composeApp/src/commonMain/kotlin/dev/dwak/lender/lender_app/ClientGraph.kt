package dev.dwak.lender.lender_app

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.dwak.lender.app.navigation.core.LenderRoute
import dev.dwak.lender.app.navigation.core.NavigationSerializers
import kotlinx.serialization.modules.SerializersModule

interface ClientGraph {
  val entryBuilders: Set<EntryProviderScope<NavKey>.() -> Unit>

  @NavigationSerializers
  val navigationSerializers: Set<SerializersModule>
}