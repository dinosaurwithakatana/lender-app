package dev.dwak.lender.lender_app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import dev.dwak.lender.feature.auth.navigation.api.AuthRoutes

@Composable
fun App(graph: ClientGraph) {
  MaterialTheme {

    Box(modifier = Modifier.fillMaxSize().safeContentPadding()) {
      val backStack = rememberSaveableBackStack(root = AuthRoutes.Launch)
      val navigator = rememberCircuitNavigator(backStack) {
        // Do something when the root screen is popped, usually exiting the app
      }
      CircuitCompositionLocals(graph.circuit) {
        NavigableCircuitContent(navigator = navigator, backStack = backStack)
      }
    }
  }
}
