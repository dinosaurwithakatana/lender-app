package dev.dwak.lender.feature.item.presenter

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.app.modification.CreateItemMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.feature.item.navigation.ItemScreens
import dev.dwak.lender.lender_app.coroutines.Io
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@CircuitInject(
  screen = ItemScreens.CreateItem::class,
  scope = AppScope::class
)
@Inject
class CreateItemPresenter(
  private val dataModifier: DataModifier,
  @Io private val ioScope: CoroutineScope
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
            dataModifier.submit(CreateItemMod(
              name = name.text.toString(),
              description = description.text.toString().ifEmpty { null },
              quantity = quantity.text.toString().toInt()
            ))
          }
        }
      }
    }
  }
}