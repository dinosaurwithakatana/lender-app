package dev.dwak.lender.lender_app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.CircuitContent
import com.slack.circuit.retained.CircuitRetainedSettings
import com.slack.circuit.retained.ExperimentalCircuitRetainedApi

@OptIn(ExperimentalCircuitRetainedApi::class)
@Composable
fun LenderApp(graph: ClientGraph) {
  CircuitRetainedSettings.useFirstParty = true
  MaterialTheme {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
      CircuitCompositionLocals(graph.circuit) {
        CircuitContent(AppScreen)
      }
    }
  }
}

