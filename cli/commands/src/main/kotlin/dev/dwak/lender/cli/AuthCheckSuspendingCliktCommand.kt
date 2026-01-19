package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.parameters.options.option
import dev.dwak.lender.models.server.ServerProfileId
import kotlinx.coroutines.runBlocking

abstract class AuthCheckSuspendingCliktCommand(private val authManager: AuthManager) :
  SuspendingCliktCommand() {
  private val forProfile by option()
  private val profileId by lazy {
    runBlocking {
      ServerProfileId(
        if (forProfile != null) {
          forProfile!!
        } else {
          authManager.currentProfile().id
        }
      )
    }
  }

  override suspend fun run() {
    if (authManager.isLoggedIn()) {
      run(profileId)
    } else {
      error("Not logged in!")
    }
  }

  abstract suspend fun run(profileId: ServerProfileId)
}