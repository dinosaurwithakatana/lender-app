package dev.dwak.lender.lender_app

import com.slack.circuit.runtime.navigation.NavStackList
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuitx.navigation.intercepting.NavigationContext
import com.slack.circuitx.navigation.intercepting.NavigationEventListener
import kotlinx.browser.window
import org.w3c.dom.Window

class BrowserNavigationEventListener : NavigationEventListener {
  override fun onNavStackChanged(
    navStack: NavStackList<Screen>?,
    navigationContext: NavigationContext
  ) {
    super.onNavStackChanged(navStack, navigationContext)
  }
}