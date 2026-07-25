package dev.dwak.lender.lender_app.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.feature.groups.navigation.GroupsScreens
import dev.dwak.lender.feature.home.navigation.HomeScreens
import dev.dwak.lender.feature.lend.navigation.LendScreens
import dev.dwak.lender.feature.profile.navigation.ProfileScreens
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject

@AssistedInject
class TabPresenter(
  @Assisted private val navigator: Navigator,
) : Presenter<TabState> {
  private val options = Navigator.StateOptions.SaveAndRestore

  @Composable
  override fun present(): TabState {
    var currentTab by remember { mutableStateOf(BottomBarTabs.HOME) }
    LaunchedEffect(currentTab) {
      when (currentTab) {
        BottomBarTabs.HOME -> navigator.resetRoot(HomeScreens.Home, options)
        BottomBarTabs.LENDS -> navigator.resetRoot(LendScreens.LendHome, options)
        BottomBarTabs.GROUPS -> navigator.resetRoot(GroupsScreens.GroupsHome, options)
        BottomBarTabs.PROFILE -> navigator.resetRoot(ProfileScreens.ProfileHome, options)
      }
    }

    return TabState(
      selectedBottomBarTabs = currentTab,
    ) { event ->
      when (event) {
        is TabEvents.TabSelected -> currentTab = event.tab
      }
    }
  }

  @AssistedFactory
  fun interface Factory {
    fun create(navigator: Navigator): TabPresenter
  }
}