package dev.dwak.lender.lender_app

import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuitx.navigation.intercepting.NavigationInterceptor

sealed interface AppEvents {
}

data class AppState(
    val navigationInterceptors: Set<NavigationInterceptor>,
    val isLoggedIn: Boolean,
    val dispatch: (AppEvents) -> Unit,
) : CircuitUiState

data object AppScreen : Screen