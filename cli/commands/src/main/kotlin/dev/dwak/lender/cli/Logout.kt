package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.binding

@ContributesIntoSet(
  scope = AppScope::class,
  binding = binding<SuspendingCliktCommand>()
)
class Logout(
  private val authManager: AuthManager
) : AuthCheckSuspendingCliktCommand(authManager) {
  override suspend fun runWithAuthCheck() {
    authManager.logout()
  }
}