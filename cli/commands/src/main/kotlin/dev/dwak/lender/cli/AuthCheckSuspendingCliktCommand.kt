package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand

abstract class AuthCheckSuspendingCliktCommand(private val authManager: AuthManager) :
  SuspendingCliktCommand() {
  override suspend fun run() {
    if (authManager.isLoggedIn()) {
      runWithAuthCheck()
    }
    else {
      error("Not logged in!")
    }
  }

  abstract suspend fun runWithAuthCheck()
}