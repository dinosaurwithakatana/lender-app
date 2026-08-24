package dev.dwak.lender.groups.presenter.addMember

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.app.modification.AddMemberMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.feature.groups.navigation.GroupsScreens
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.lender.repos.client.ProfileRepo
import dev.dwak.models.client.ClientGroup
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AssistedInject
class AddMemberPresenter(
  @Assisted private val navigator: Navigator,
  @Assisted private val screen: GroupsScreens.AddMember,
  private val profileRepo: ProfileRepo,
  private val dataModifier: DataModifier,
  @Io private val ioScope: CoroutineScope,
) : Presenter<AddMemberState> {

  @Composable
  override fun present(): AddMemberState {
    val email = rememberTextFieldState()
    var lookup by remember { mutableStateOf<AddMemberState.LookupResult>(AddMemberState.LookupResult.Idle) }
    var submitting by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    return AddMemberState(
      email = email,
      lookup = lookup,
      submitting = submitting,
      errorMessage = errorMessage,
    ) { event ->
      when (event) {
        AddMemberEvents.Back -> navigator.pop()

        AddMemberEvents.Search -> {
          val query = email.text.toString().trim()
          if (query.isNotEmpty()) {
            errorMessage = null
            lookup = AddMemberState.LookupResult.Searching
            ioScope.launch {
              val profile = profileRepo.lookupByEmail(query)
              lookup = if (profile != null) {
                AddMemberState.LookupResult.Found(profile)
              } else {
                AddMemberState.LookupResult.NotFound
              }
            }
          }
        }

        AddMemberEvents.Invite -> {
          val found = lookup as? AddMemberState.LookupResult.Found
          if (found != null && !submitting) {
            submitting = true
            errorMessage = null
            ioScope.launch {
              when (
                dataModifier.submit(
                  AddMemberMod(
                    groupId = ClientGroup.Id(screen.groupId),
                    profileId = found.profile.id,
                  )
                )
              ) {
                AddMemberMod.Result.Success -> {
                  navigator.pop(result = GroupsScreens.AddMember.MemberInvitedResult)
                }
                AddMemberMod.Result.Error -> {
                  submitting = false
                  errorMessage = "Could not invite. Please try again."
                }
              }
            }
          }
        }
      }
    }
  }

  @CircuitInject(
    screen = GroupsScreens.AddMember::class,
    scope = AppScope::class,
  )
  @AssistedFactory
  fun interface Factory {
    fun create(navigator: Navigator, screen: GroupsScreens.AddMember): AddMemberPresenter
  }
}
