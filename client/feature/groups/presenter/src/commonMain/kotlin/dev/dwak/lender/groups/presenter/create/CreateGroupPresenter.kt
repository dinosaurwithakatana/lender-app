package dev.dwak.lender.groups.presenter.create

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.app.modification.CreateGroupMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.feature.groups.navigation.GroupsScreens
import dev.dwak.lender.lender_app.coroutines.Io
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AssistedInject
class CreateGroupPresenter(
  private val dataModifier: DataModifier,
  @Io private val ioScope: CoroutineScope,
  @Assisted private val navigator: Navigator,
) : Presenter<CreateGroupState> {
  @Composable
  override fun present(): CreateGroupState {
    val name = rememberTextFieldState()
    return CreateGroupState(
      name = name,
    ) { event ->
      when (event) {
        CreateGroupEvents.AttemptSave -> {
          ioScope.launch {
            when (dataModifier.submit(CreateGroupMod(name = name.text.toString()))) {
              CreateGroupMod.Result.Error -> TODO()
              is CreateGroupMod.Result.Success -> {
                navigator.pop(result = GroupsScreens.CreateGroup.GroupCreatedResult)
              }
            }
          }
        }
        CreateGroupEvents.Back -> {
          navigator.backward()
        }
      }
    }
  }

  @CircuitInject(
    screen = GroupsScreens.CreateGroup::class,
    scope = AppScope::class
  )
  @AssistedFactory
  fun interface Factory {
    fun create(navigator: Navigator): CreateGroupPresenter
  }
}
