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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dev.dwak.lender.app.navigation.core.AppBackStack
import dev.dwak.lender.app.navigation.core.LenderRoute
import dev.dwak.lender.app.navigation.core.LoggedInRoutes
import dev.dwak.lender.feature.auth.ui.AuthRoutes
import dev.dwak.lender.feature.auth.ui.login.LoginUi
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(graph: ClientGraph) {
  MaterialTheme {
    var showContent by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().safeContentPadding()) {
      NavigationApp(graph.entryBuilders)
    }
  }
}

@Composable
fun NavigationApp(entryBuilders: Set<EntryProviderScope<LenderRoute>.() -> Unit>) {
  val backStack = remember {
    AppBackStack(
      startRoute = LoggedInRoutes.Home,
      loginRoute = AuthRoutes.Launch
    )
  }
  NavDisplay(
    backStack = backStack.backStack,
    onBack = { backStack.remove() },
    entryProvider = entryProvider {
      entry<LoggedInRoutes.Home> {
        Text("Home")
      }

      entryBuilders.forEach { b->
        b(this@entryProvider)
      }
    }
  )
}