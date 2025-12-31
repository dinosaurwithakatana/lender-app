package dev.dwak.lender.app.navigation.core

import androidx.navigation3.runtime.NavBackStack

class Navigator(
  private val backStack: NavBackStack<LenderRoute>,
  private val onNavigateToRestrictedKey: (targetKey: LenderRoute?) -> LenderRoute,
  private val isLoggedIn: () -> Boolean,
) {
  fun navigate(key: LenderRoute) {
    if (key is AuthenticatedLenderRoute && !isLoggedIn()) {
      val loginKey = onNavigateToRestrictedKey(key)
      backStack.add(loginKey)
    } else {
      backStack.add(key)
    }
  }

  fun goBack() = backStack.removeLastOrNull()
}