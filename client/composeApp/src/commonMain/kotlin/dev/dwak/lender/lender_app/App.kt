package dev.dwak.lender.lender_app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.CircuitContent
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuitx.gesturenavigation.GestureNavigationDecorationFactory
import com.slack.circuitx.navigation.intercepting.NavigationInterceptor
import com.slack.circuitx.navigation.intercepting.rememberInterceptingNavigator
import dev.dwak.lender.feature.home.navigation.HomeRoutes

@Composable
fun App(graph: ClientGraph) {
  MaterialTheme {
    Box(modifier = Modifier.fillMaxSize().safeContentPadding()) {
      CircuitCompositionLocals(graph.circuit) {
        CircuitContent(AppScreen)
      }
    }
  }
}

