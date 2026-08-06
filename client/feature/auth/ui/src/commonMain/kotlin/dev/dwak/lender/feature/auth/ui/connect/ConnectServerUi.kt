package dev.dwak.lender.feature.auth.ui.connect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.feature.auth.navigation.api.AuthScreens
import dev.dwak.lender.feature.auth.presenter.connect.ConnectServerEvents
import dev.dwak.lender.feature.auth.presenter.connect.ConnectServerState
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

@CircuitInject(
  screen = AuthScreens.ConnectServer::class,
  scope = AppScope::class
)
@Inject
class ConnectServerUi : Ui<ConnectServerState> {
  @Composable
  override fun Content(
    state: ConnectServerState,
    modifier: Modifier,
  ) {
    Column(
      modifier = modifier.fillMaxSize().padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      Text("Connect to server", style = MaterialTheme.typography.headlineSmall)
      Text("Server URL")
      TextField(
        state = state.serverUrl,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
        enabled = !state.isSaving,
      )
      Text("API key")
      SecureTextField(
        state = state.apiKey,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        enabled = !state.isSaving,
      )
      state.errorMessage?.let {
        Text(it, color = MaterialTheme.colorScheme.error, maxLines = 3)
      }
      Button(
        onClick = { state.dispatch(ConnectServerEvents.Connect) },
        enabled = !state.isSaving,
      ) {
        Text(if (state.isSaving) "Connecting…" else "Connect")
      }
    }
  }
}
