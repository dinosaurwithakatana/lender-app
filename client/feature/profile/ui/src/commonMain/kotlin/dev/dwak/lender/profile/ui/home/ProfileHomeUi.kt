package dev.dwak.lender.profile.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.calf.ui.ExperimentalCalfUiApi
import com.mohamedrejeb.calf.ui.navigation.AdaptiveScaffold
import com.mohamedrejeb.calf.ui.navigation.AdaptiveTopBar
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.feature.profile.navigation.ProfileScreens
import dev.dwak.lender.profile.presenter.home.ProfileHomeEvents
import dev.dwak.lender.profile.presenter.home.ProfileHomeState
import dev.dwak.models.client.ClientProfile
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

@CircuitInject(
  screen = ProfileScreens.ProfileHome::class,
  scope = AppScope::class
)
@Inject
class ProfileHomeUi : Ui<ProfileHomeState> {

  @OptIn(ExperimentalMaterial3Api::class, ExperimentalCalfUiApi::class)
  @Composable
  override fun Content(
    state: ProfileHomeState,
    modifier: Modifier,
  ) {
    AdaptiveScaffold(
      modifier = modifier,
      topBar = { AdaptiveTopBar(iosTitle = "Profile") },
    ) { padding ->
      PullToRefreshBox(
        modifier = Modifier.fillMaxSize().padding(padding),
        isRefreshing = state.refreshing,
        onRefresh = { state.dispatch(ProfileHomeEvents.Refresh) },
      ) {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
          state.profile?.let { ProfileSection(it) }
          Section(title = "Invite Links") {
            Text("Coming soon.", style = MaterialTheme.typography.bodySmall)
          }
          Section(title = "Invite History") {
            Text("Coming soon.", style = MaterialTheme.typography.bodySmall)
          }
        }
      }
    }
  }
}

@Composable
private fun ProfileSection(profile: ClientProfile) {
  Column {
    Text(
      "${profile.firstName} ${profile.lastName}".trim(),
      style = MaterialTheme.typography.headlineMedium,
    )
    Text(
      profile.id.id,
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
  }
}

@Composable
private fun Section(title: String, content: @Composable () -> Unit) {
  Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    Text(title, style = MaterialTheme.typography.titleMedium)
    HorizontalDivider()
    content()
  }
}
