package dev.dwak.lender.feature.item.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.feature.item.navigation.ItemRoutes
import dev.dwak.lender.feature.item.presenter.CreateItemState
import dev.zacsweers.metro.AppScope

@CircuitInject(
  screen = ItemRoutes.CreateItem::class,
  scope = AppScope::class
)
class CreateItemUi : Ui<CreateItemState> {
  @Composable
  override fun Content(
    state: CreateItemState,
    modifier: Modifier
  ) {
    CreateItem(state)
  }
}

@Composable
fun CreateItem(state: CreateItemState) {
  Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
    TextField(
      state = state.name,
      label = {
        Text("Name")
      }
    )

    TextField(
      state = state.description,
      label = {
        Text("Description")
      }
    )
    TextField(
      state = state.quantity,
      label = {
        Text("Quantity")
      },
      keyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Number
      )
    )
  }
}

@Composable
@Preview
fun CreateItemPreview() {
  CreateItem(
    state = CreateItemState(
      name = TextFieldState(),
      description = TextFieldState(),
      quantity = TextFieldState(),
      dispatch = {}
    )
  )
}