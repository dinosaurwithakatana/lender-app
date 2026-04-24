package dev.dwak.lender.lender_app

import androidx.compose.ui.window.ComposeUIViewController
import dev.zacsweers.metro.createGraph
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
  val graph = createGraph<IosClientGraph>()
  Napier.base(DebugAntilog())
  return ComposeUIViewController { App(graph) }
}