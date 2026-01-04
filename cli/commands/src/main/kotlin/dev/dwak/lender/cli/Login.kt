package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
class Login(
  private val authManager: AuthManager
) : SuspendingCliktCommand() {
  val email by option().prompt()
  val password by option().prompt(
    hideInput = true
  )

  override suspend fun run() {
    val result = authManager.login(email, password)
    if (result.isSuccess) {
      echo("Logged in!")
    } else {
      echo("Failed!")
    }
  }
}