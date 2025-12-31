package dev.dwak.lender.lender_app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.serialization.NavBackStackSerializer
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import dev.dwak.lender.app.navigation.core.LenderRoute
import dev.dwak.lender.app.navigation.core.LenderRouteEntryProvider
import dev.dwak.lender.app.navigation.core.LoggedInRoutes
import dev.dwak.lender.app.navigation.core.Navigator
import dev.dwak.lender.feature.auth.navigation.api.AuthRoutes
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(graph: ClientGraph) {
  MaterialTheme {
    var showContent by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().safeContentPadding()) {
      NavigationApp(
        entryBuilders = graph.entryBuilders,
        navigationSerializers = graph.navigationSerializers
      )
    }
  }
}

@Composable
fun NavigationApp(
  entryBuilders: Set<LenderRouteEntryProvider>,
  navigationSerializers: Set<SerializersModule>
) {
  val configuration = SavedStateConfiguration {
    serializersModule = SerializersModule {
      polymorphic(LenderRoute::class) {
        navigationSerializers.forEach { include(it) }
      }
    }
  }
  val backStack = rememberSerializable(
    configuration = configuration,
    serializer = NavBackStackSerializer(PolymorphicSerializer(LenderRoute::class))
  ) {
    NavBackStack(AuthRoutes.Launch)
  }
  val navigator = Navigator(
    backStack = backStack,
    onNavigateToRestrictedKey = { AuthRoutes.Launch },
    isLoggedIn = { false }
  )
  NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLast() },
    entryProvider = entryProvider {
      entry<LoggedInRoutes.Home> {
        Text("Home")
      }

      entryBuilders.forEach { b ->
        b(this@entryProvider)
      }
    }
  )
}