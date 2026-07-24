package dev.dwak.lender.lend.presenter.create

import androidx.compose.foundation.text.input.rememberTextFieldState
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
import dev.dwak.lender.app.modification.CreateGuestLendMod
import dev.dwak.lender.app.modification.CreateLendMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.feature.lend.navigation.LendScreens
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.lender.repos.client.GroupsRepo
import dev.dwak.lender.repos.client.ItemRepo
import dev.dwak.lender.repos.client.RepoRefresher
import dev.dwak.models.client.ClientGroup
import dev.dwak.models.client.ClientItem
import dev.dwak.models.client.ClientLendStatus
import dev.dwak.models.client.ClientProfile
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AssistedInject
class CreateLendPresenter(
  @Assisted private val navigator: Navigator,
  private val dataModifier: DataModifier,
  @Io private val ioScope: CoroutineScope,
  private val itemRepo: ItemRepo,
  private val itemRepoRefresher: RepoRefresher<ItemRepo.RefreshTypes>,
  private val groupsRepo: GroupsRepo,
  private val groupsRepoRefresher: RepoRefresher<GroupsRepo.RefreshTypes>,
) : Presenter<CreateLendState> {

  @Composable
  override fun present(): CreateLendState {
    val items by itemRepo.items.collectAsRetainedState(emptyList())
    val groups by groupsRepo.currentUserGroups.collectAsRetainedState(emptyList())

    var mode by remember { mutableStateOf(LendMode.GUEST) }
    var selectedItem by remember { mutableStateOf<ClientItem?>(null) }
    var selectedGroup by remember { mutableStateOf<ClientGroup?>(null) }
    var selectedMember by remember { mutableStateOf<ClientProfile?>(null) }
    var members by remember { mutableStateOf<List<ClientProfile>>(emptyList()) }
    var submitting by remember { mutableStateOf(false) }

    val firstName = rememberTextFieldState()
    val lastName = rememberTextFieldState()
    val quantity = rememberTextFieldState("1")

    LaunchedEffect(Unit) {
      itemRepoRefresher.refresh(ItemRepo.RefreshTypes.AllItems)
      groupsRepoRefresher.refresh(GroupsRepo.RefreshTypes.CurrentUserGroups)
    }

    LaunchedEffect(selectedGroup) {
      val group = selectedGroup
      if (group != null) {
        members = groupsRepo.getMembers(group.id)
        selectedMember = null
      } else {
        members = emptyList()
      }
    }

    return CreateLendState(
      mode = mode,
      items = items,
      groups = groups,
      members = members,
      selectedItem = selectedItem,
      selectedGroup = selectedGroup,
      selectedMember = selectedMember,
      firstName = firstName,
      lastName = lastName,
      quantity = quantity,
      submitting = submitting,
    ) { event ->
      when (event) {
        CreateLendEvents.Back -> navigator.backward()
        is CreateLendEvents.SelectItem -> selectedItem = event.item
        is CreateLendEvents.SelectMode -> mode = event.mode
        is CreateLendEvents.SelectGroup -> selectedGroup = event.group
        is CreateLendEvents.SelectMember -> selectedMember = event.member
        CreateLendEvents.AttemptSave -> {
          val item = selectedItem ?: return@CreateLendState
          val qty = quantity.text.toString().toIntOrNull() ?: return@CreateLendState

          submitting = true
          ioScope.launch {
            val success = when (mode) {
              LendMode.GUEST -> {
                val result = dataModifier.submit(
                  CreateGuestLendMod(
                    itemId = item.id,
                    firstName = firstName.text.toString(),
                    lastName = lastName.text.toString(),
                    quantity = qty,
                    lendStatus = ClientLendStatus.APPROVED
                  )
                )
                result is CreateGuestLendMod.Result.Success
              }
              LendMode.GROUP_MEMBER -> {
                val group = selectedGroup ?: return@launch run { submitting = false }
                val member = selectedMember ?: return@launch run { submitting = false }
                val result = dataModifier.submit(
                  CreateLendMod(
                    itemId = item.id,
                    groupId = group.id,
                    toProfileId = member.id,
                    quantity = qty,
                  )
                )
                result is CreateLendMod.Result.Success
              }
            }
            submitting = false
            if (success) {
              navigator.pop(result = LendScreens.CreateLend.LendCreatedResult)
            }
          }
        }
      }
    }
  }

  @CircuitInject(
    screen = LendScreens.CreateLend::class,
    scope = AppScope::class
  )
  @AssistedFactory
  fun interface Factory {
    fun create(navigator: Navigator): CreateLendPresenter
  }
}
