package dev.dwak.lender.feature.item.presenter

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.app.modification.CreateItemMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.feature.item.navigation.ItemScreens
import dev.dwak.lender.lender_app.coroutines.Io
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AssistedInject
class CreateItemPresenter(
  private val dataModifier: DataModifier,
  @Io private val ioScope: CoroutineScope,
  @Assisted private val navigator: Navigator,
) : Presenter<CreateItemState> {

  @Composable
  override fun present(): CreateItemState {
    val name = rememberTextFieldState()
    val description = rememberTextFieldState()
    val quantity = rememberTextFieldState()
    return CreateItemState(
      name = name,
      description = description,
      quantity = quantity
    ) { event ->
      when (event) {
        CreateItemEvents.AttemptSave -> {
          ioScope.launch {
            when(dataModifier.submit(CreateItemMod(
              name = name.text.toString(),
              description = description.text.toString().ifEmpty { null },
              quantity = quantity.text.toString().toInt()
            ))) {
              CreateItemMod.Result.Error -> TODO()
              is CreateItemMod.Result.Success -> {
                navigator.pop(result = ItemScreens.CreateItem.ItemCreatedResult)
              }
            }
          }
        }
        CreateItemEvents.Back -> {
          navigator.backward()
        }
      }
    }
  }

  @CircuitInject(
    screen = ItemScreens.CreateItem::class,
    scope = AppScope::class
  )
  @AssistedFactory
  fun interface Factory {
    fun create(
      navigator: Navigator,
    ): CreateItemPresenter
  }
}