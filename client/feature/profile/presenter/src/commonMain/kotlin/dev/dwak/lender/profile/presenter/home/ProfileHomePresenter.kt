package dev.dwak.lender.profile.presenter.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.app.modification.LogoutMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.feature.profile.navigation.ProfileScreens
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.lender.repos.client.ProfileRepo
import dev.dwak.lender.repos.client.RepoRefresher
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AssistedInject
class ProfileHomePresenter(
  @Assisted private val navigator: Navigator,
  private val profileRepo: ProfileRepo,
  private val profileRepoRefresher: RepoRefresher<ProfileRepo.RefreshTypes>,
  private val dataModifier: DataModifier,
  @Io private val ioScope: CoroutineScope,
) : Presenter<ProfileHomeState> {

  @Composable
  override fun present(): ProfileHomeState {
    val profile by profileRepo.currentProfile.collectAsRetainedState(null)
    var isLoading by remember { mutableStateOf(true) }
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isLoading, isRefreshing) {
      if (isLoading || isRefreshing) {
        profileRepoRefresher.refresh(ProfileRepo.RefreshTypes.CurrentProfile)

        isLoading = false
        isRefreshing = false
      }
    }

    return ProfileHomeState(
      profile = profile,
      loading = isLoading,
      refreshing = isRefreshing,
    ) { event ->
      when (event) {
        ProfileHomeEvents.Refresh -> isRefreshing = true
        ProfileHomeEvents.Logout ->{
          ioScope.launch {
            dataModifier.submit(LogoutMod)
          }
        }
      }
    }
  }

  @CircuitInject(
    screen = ProfileScreens.ProfileHome::class,
    scope = AppScope::class
  )
  @AssistedFactory
  fun interface Factory {
    fun create(navigator: Navigator): ProfileHomePresenter
  }
}
