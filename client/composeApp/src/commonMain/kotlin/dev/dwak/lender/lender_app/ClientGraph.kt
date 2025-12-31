package dev.dwak.lender.lender_app

import androidx.savedstate.serialization.SavedStateConfiguration
import dev.dwak.lender.app.navigation.LenderRouteEntryProvider
import dev.dwak.lender.app.navigation.NavigationSerializers
import dev.dwak.lender.app.navigation.Navigator
import kotlinx.serialization.modules.SerializersModule

interface ClientGraph {
  val entryBuilders: Set<LenderRouteEntryProvider>

  val savedStateConfiguration: SavedStateConfiguration
}