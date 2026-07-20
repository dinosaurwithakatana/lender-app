package dev.dwak.lender.feature.home.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.app.modification.LogoutMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.feature.home.navigation.HomeScreens
import dev.dwak.lender.feature.item.navigation.ItemScreens
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.lender.repos.client.ItemRepo
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
) : Presenter<HomeState> {
  @Composable
  override fun present(): HomeState {
    val state by produceState(
      initialValue = HomeState(emptyList(), dispatch = {
        when (it) {
          HomeEvents.Logout -> {
            ioScope.launch {
              dataModifier.submit(LogoutMod)
            }
          }

          HomeEvents.NavigateToCreateItem -> {
            navigator.goTo(ItemScreens.CreateItem)
          }
        }
      }),
      producer = {
        value = value.copy(items = itemRepo.items())
      }
    )

    return state
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