package dev.dwak.lender.lend.presenter.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.foundation.rememberAnsweringNavigator
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.app.modification.AdvanceLendMod
import dev.dwak.lender.app.modification.DeleteLendMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.feature.lend.navigation.LendScreens
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.lender.repos.client.LendsRepo
import dev.dwak.lender.repos.client.RepoRefresher
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AssistedInject
class LendHomePresenter(
  @Assisted private val navigator: Navigator,
  private val lendsRepo: LendsRepo,
  private val lendsRepoRefresher: RepoRefresher<LendsRepo.RefreshTypes>,
  private val dataModifier: DataModifier,
  @Io private val ioScope: CoroutineScope,
) : Presenter<LendHomeState> {

  @Composable
  override fun present(): LendHomeState {
    val lends by lendsRepo.currentUserLends.collectAsRetainedState(emptyList())
    var isLoading by remember { mutableStateOf(true) }
    var isRefreshing by remember { mutableStateOf(false) }

    val createLendNavigator = rememberAnsweringNavigator<LendScreens.CreateLend.LendCreatedResult>(navigator) {
      isRefreshing = true
    }

    LaunchedEffect(isLoading, isRefreshing) {
      if (isLoading || isRefreshing) {
        lendsRepoRefresher.refresh(LendsRepo.RefreshTypes.CurrentUserLends)

        isLoading = false
        isRefreshing = false
      }
    }

    return LendHomeState(
      lends = lends,
      loading = isLoading,
      refreshing = isRefreshing,
    ) { event ->
      when (event) {
        LendHomeEvents.AddLend -> createLendNavigator.goTo(LendScreens.CreateLend)
        LendHomeEvents.Refresh -> isRefreshing = true
        is LendHomeEvents.DeleteLend -> {
          ioScope.launch {
            val result = dataModifier.submit(DeleteLendMod(lendId = event.lend.id))
            if (result is DeleteLendMod.Result.Success) {
              isRefreshing = true
            }
          }
        }
        is LendHomeEvents.AdvanceLend -> {
          ioScope.launch {
            val result = dataModifier.submit(AdvanceLendMod(lendId = event.lend.id))
            if (result is AdvanceLendMod.Result.Success) {
              isRefreshing = true
            }
          }
        }
      }
    }
  }

  @CircuitInject(
    screen = LendScreens.LendHome::class,
    scope = AppScope::class
  )
  @AssistedFactory
  fun interface Factory {
    fun create(navigator: Navigator): LendHomePresenter
  }
}
