package dev.dwak.lender.groups.presenter.home

import androidx.compose.runtime.Composable
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.feature.groups.navigation.GroupsScreens
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject

@AssistedInject
class GroupsHomePresenter(
  @Assisted private val navigator: Navigator
): Presenter<GroupsHomeState> {

  @Composable
  override fun present(): GroupsHomeState {
    return GroupsHomeState() {
      
    }
  }

  @CircuitInject(
    screen = GroupsScreens.GroupsHome::class,
    scope = AppScope::class
  )
  @AssistedFactory
  fun interface Factory {
    fun create(navigator: Navigator): GroupsHomePresenter
  }

}