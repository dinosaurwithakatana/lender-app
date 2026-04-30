package dev.dwak.lender.feature.item.presenter

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import dev.dwak.lender.feature.item.navigation.ItemRoutes
import dev.zacsweers.metro.AppScope

@CircuitInject(
  screen = ItemRoutes.CreateItem::class,
  scope = AppScope::class
)
class CreateItemPresenter : Presenter<CreateItemState> {
  @Composable
  override fun present(): CreateItemState {
    val name = rememberTextFieldState()
    val description = rememberTextFieldState()
    val quantity = rememberTextFieldState()
    return CreateItemState(
      name = name,
      description = description,
      quantity = quantity
    ) {

    }
  }
}