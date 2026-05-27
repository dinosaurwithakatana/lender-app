package dev.dwak.lender.lender_app

import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuitx.navigation.intercepting.NavigationEventListener
import com.slack.circuitx.navigation.intercepting.NavigationInterceptor
import kotlinx.serialization.Serializable

sealed interface AppEvents {
}

data class AppState(
    val navigationInterceptors: Set<NavigationInterceptor>,
    val navigationEventInterceptors: Set<NavigationEventListener>,
    val isLoggedIn: Boolean = false,
    val dispatch: (AppEvents) -> Unit,
) : CircuitUiState

@Parcelize
@Serializable
data object AppScreen : Screen