package dev.dwak.lender.groups.presenter.home

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
import dev.dwak.lender.feature.groups.navigation.GroupsScreens
import dev.dwak.lender.repos.client.GroupsRepo
import dev.dwak.lender.repos.client.RepoRefresher
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject

@AssistedInject
class GroupsHomePresenter(
  @Assisted private val navigator: Navigator,
  private val groupsRepo: GroupsRepo,
  private val groupsRepoRefresher: RepoRefresher<GroupsRepo.RefreshTypes>
) : Presenter<GroupsHomeState> {

  @Composable
  override fun present(): GroupsHomeState {
    val groups by groupsRepo.currentUserGroups.collectAsRetainedState(emptyList())
    var isLoading by remember { mutableStateOf(true) }
    var isRefreshing by remember { mutableStateOf(false) }

    val createGroupNavigator = rememberAnsweringNavigator<GroupsScreens.CreateGroup.GroupCreatedResult>(navigator) {
      isRefreshing = true
    }

    LaunchedEffect(isLoading, isRefreshing) {
      if (isLoading || isRefreshing) {
        groupsRepoRefresher.refresh(GroupsRepo.RefreshTypes.CurrentUserGroups)

        isLoading = false
        isRefreshing = false
      }
    }

    return GroupsHomeState(
      groups = groups,
      loading = isLoading,
      refreshing = isRefreshing,
    ) {
      when (it) {
        GroupsHomeEvents.CreateGroup -> createGroupNavigator.goTo(GroupsScreens.CreateGroup)
      }
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