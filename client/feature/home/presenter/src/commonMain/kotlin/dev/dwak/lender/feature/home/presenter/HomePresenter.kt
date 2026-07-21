package dev.dwak.lender.feature.home.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.app.modification.LogoutMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.feature.home.navigation.HomeScreens
import dev.dwak.lender.feature.item.navigation.ItemScreens
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.lender.repos.client.ItemRepo
import dev.dwak.lender.repos.client.RepoRefresher
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AssistedInject
class HomePresenter(
  private val dataModifier: DataModifier,
  @Io private val ioScope: CoroutineScope,
  @Assisted private val navigator: Navigator,
  private val itemRepo: ItemRepo,
  private val itemRepoRefresher: RepoRefresher<ItemRepo.RefreshTypes>
) : Presenter<HomeState> {
  @Composable
  override fun present(): HomeState {
    var isLoading by rememberRetained { mutableStateOf(true) }
    var isRefreshing by rememberRetained { mutableStateOf(false) }

    LaunchedEffect(isLoading, isRefreshing) {
      if (isLoading || isRefreshing) {
        itemRepoRefresher.refresh(ItemRepo.RefreshTypes.AllItems)

        isLoading = false
        isRefreshing = false
      }
    }


    return HomeState(
      items = itemRepo.items.collectAsRetainedState(emptyList()).value,
      loading = isLoading,
      refreshing = isRefreshing,
      dispatch = {
        when (it) {
          HomeEvents.Logout -> {
            ioScope.launch {
              dataModifier.submit(LogoutMod)
            }
          }

          HomeEvents.NavigateToCreateItem -> {
            navigator.goTo(ItemScreens.CreateItem)
          }

          HomeEvents.Refresh -> {
            isRefreshing = true
          }
        }
      },
    )
  }

  @CircuitInject(
    screen = HomeScreens.Home::class,
    scope = AppScope::class
  )
  @AssistedFactory
  fun interface Factory {
    fun create(navigator: Navigator): HomePresenter
  }
}