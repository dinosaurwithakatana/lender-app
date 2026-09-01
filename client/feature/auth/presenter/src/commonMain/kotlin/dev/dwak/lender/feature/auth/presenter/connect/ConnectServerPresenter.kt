package dev.dwak.lender.feature.auth.presenter.connect

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.app.modification.SaveServerConfigMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.feature.auth.navigation.api.AuthScreens
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.launch

@CircuitInject(
  screen = AuthScreens.ConnectServer::class,
  scope = AppScope::class
)
@Inject
class ConnectServerPresenter(
  private val dataModifier: DataModifier,
) : Presenter<ConnectServerState> {
  @Composable
  override fun present(): ConnectServerState {
    val scope = rememberCoroutineScope()
    val serverUrl = rememberTextFieldState()
    val apiKey = rememberTextFieldState()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isSaving by remember { mutableStateOf(false) }
    var revealApiKey by remember { mutableStateOf(false) }

    return ConnectServerState(
      serverUrl = serverUrl,
      apiKey = apiKey,
      revealApiKey = revealApiKey,
      errorMessage = errorMessage,
      isSaving = isSaving,
      dispatch = { event ->
        when (event) {
          ConnectServerEvents.ToggleApiKeyRevealed -> {
            revealApiKey = !revealApiKey
          }
          ConnectServerEvents.Connect -> {
            val url = serverUrl.text.toString().trim().trimEnd('/')
            val key = apiKey.text.toString().trim()
            if (url.isBlank() || key.isBlank()) {
              errorMessage = "Server URL and API key are required"
              return@ConnectServerState
            }
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
              errorMessage = "Server URL must start with http:// or https://"
              return@ConnectServerState
            }
            errorMessage = null
            isSaving = true
            scope.launch {
              val result = dataModifier.submit(
                SaveServerConfigMod(serverUrl = url, apiKey = key)
              )
              when (result) {
                SaveServerConfigMod.Result.Success -> Unit
                is SaveServerConfigMod.Result.Error -> errorMessage = result.message
              }
              isSaving = false
            }
          }
        }
      }
    )
  }
}
