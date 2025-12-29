package dev.dwak.lender.lender_app

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import dev.zacsweers.metro.createGraph

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
  val graph = createGraph<WebClientGraph>()
  ComposeViewport {
    App(graph)
  }
}