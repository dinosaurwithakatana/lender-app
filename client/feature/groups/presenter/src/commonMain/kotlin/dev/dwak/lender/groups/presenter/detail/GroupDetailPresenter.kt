package dev.dwak.lender.groups.presenter.detail

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
import dev.dwak.lender.feature.groups.navigation.GroupsScreens
import dev.dwak.lender.repos.client.GroupsRepo
import dev.dwak.lender.repos.client.RepoRefresher
import dev.dwak.models.client.ClientGroup
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject

@AssistedInject
class GroupDetailPresenter(
  @Assisted private val navigator: Navigator,
  @Assisted private val screen: GroupsScreens.GroupDetail,
  private val groupsRepo: GroupsRepo,
  private val groupsRepoRefresher: RepoRefresher<GroupsRepo.RefreshTypes>,
) : Presenter<GroupDetailState> {

  @Composable
  override fun present(): GroupDetailState {
    val groupId = ClientGroup.Id(screen.groupId)
    val detail by groupsRepo.groupDetail(groupId).collectAsRetainedState(null)
    var isLoading by remember { mutableStateOf(true) }
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isLoading, isRefreshing) {
      if (isLoading || isRefreshing) {
        groupsRepoRefresher.refresh(GroupsRepo.RefreshTypes.GroupDetail(groupId))

        isLoading = false
        isRefreshing = false
      }
    }

    return GroupDetailState(
      detail = detail,
      loading = isLoading,
      refreshing = isRefreshing,
    ) { event ->
      when (event) {
        GroupDetailEvents.Back -> navigator.pop()
        GroupDetailEvents.Refresh -> isRefreshing = true
      }
    }
  }

  @CircuitInject(
    screen = GroupsScreens.GroupDetail::class,
    scope = AppScope::class,
  )
  @AssistedFactory
  fun interface Factory {
    fun create(navigator: Navigator, screen: GroupsScreens.GroupDetail): GroupDetailPresenter
  }
}
