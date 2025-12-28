package dev.dwak.lender.lender_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.createGraph

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)

    val graph = createGraph<ClientGraph>()
    setContent {
      App(graph)
    }
  }
}

@Preview
@Composable
fun AppAndroidPreview() {
  App(object : ClientGraph {
    override val entryBuilders: Set<EntryProviderScope<NavKey>.() -> Unit>
      get() = TODO("Not yet implemented")
  })
}