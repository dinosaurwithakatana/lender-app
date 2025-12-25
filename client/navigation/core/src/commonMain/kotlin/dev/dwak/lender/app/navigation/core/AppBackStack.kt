package dev.dwak.lender.app.navigation.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * A back stack that can handle routes that require login.
 *
 * @param startRoute The starting route
 * @param loginRoute The route that users should be taken to when they attempt to access a route
 * that requires login
 */
class AppBackStack<T : Any>(startRoute: T, private val loginRoute: T) {

  private var onLoginSuccessRoute: T? = null

  var isLoggedIn by mutableStateOf(false)
    private set

  val backStack = mutableStateListOf(startRoute)

  fun add(route: T) {
    if (route is AuthenticatedLenderRoute && !isLoggedIn) {
      // Store the intended destination and redirect to login
      onLoginSuccessRoute = route
      backStack.add(loginRoute)
    } else {
      backStack.add(route)
    }

    // If the user explicitly requested the login route, don't redirect them after login
    if (route == loginRoute) {
      onLoginSuccessRoute = null
    }
  }

  fun remove() = backStack.removeLastOrNull()

  fun login() {
    isLoggedIn = true

    onLoginSuccessRoute?.let {
      backStack.add(it)
      backStack.remove(loginRoute)
    }
  }

  fun logout() {
    isLoggedIn = false
    backStack.removeAll { it is AuthenticatedLenderRoute }
  }
}