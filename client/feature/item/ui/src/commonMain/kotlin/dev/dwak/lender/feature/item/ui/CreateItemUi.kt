package dev.dwak.lender.feature.item.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberWideNavigationRailState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.mohamedrejeb.calf.ui.ExperimentalCalfUiApi
import com.mohamedrejeb.calf.ui.navigation.AdaptiveScaffold
import com.mohamedrejeb.calf.ui.navigation.AdaptiveTopBar
import com.mohamedrejeb.calf.ui.navigation.UIKitUIBarButtonItem
import com.mohamedrejeb.calf.ui.navigation.UIKitUIBarButtonSystemItem
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.ui.Ui
import dev.dwak.lender.feature.item.navigation.ItemScreens
import dev.dwak.lender.feature.item.presenter.CreateItemEvents
import dev.dwak.lender.feature.item.presenter.CreateItemState
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

@CircuitInject(
  screen = ItemScreens.CreateItem::class,
  scope = AppScope::class
)
@Inject
class CreateItemUi : Ui<CreateItemState> {
  @OptIn(ExperimentalMaterial3Api::class, ExperimentalCalfUiApi::class)
  @Composable
  override fun Content(
    state: CreateItemState,
    modifier: Modifier
  ) {
    AdaptiveScaffold(
      modifier = modifier,
      topBar = {
        AdaptiveTopBar(
          iosTitle = "Create Item",
          iosLeadingItems = listOf(
            UIKitUIBarButtonItem.title(
              title = "Back",
              onClick = {
                state.dispatch(CreateItemEvents.Back)
              }
            )
          )
        )
      },
    ) {
      CreateItem(
        modifier = Modifier.padding(it),
        state = state
      )
    }
  }
}

@Composable
fun CreateItem(
  modifier: Modifier = Modifier,
  state: CreateItemState
) {
  Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
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
    Button(onClick = {
      state.dispatch(CreateItemEvents.AttemptSave)
    }) {
      Text("Save")
    }
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