package dev.dwak.lender.lender_app

import androidx.compose.ui.window.ComposeUIViewController
import dev.zacsweers.metro.createGraph
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
  val graph = createGraph<IosClientGraph>()
  return ComposeUIViewController { App(graph) }
}