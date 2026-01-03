package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import dev.dwak.lender.data.modification.auth.CreateAdminUser
import dev.dwak.lender.data.modifier.DataModifier
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
class CreateAdminUser(
  private val dataModifier: DataModifier,
) : SuspendingCliktCommand() {
  val email by option().prompt()
  val password by option().prompt(hideInput = true, requireConfirmation = true)
  val firstName by option().prompt(default = "Admin")
  val lastName by option().prompt(default = "User")

  override suspend fun run() {
    when (val result = dataModifier.submit(
      CreateAdminUser(
        email = email,
        password = password,
        firstName = firstName,
        lastName = lastName
      )
    )) {
      CreateAdminUser.Result.InvalidEmail -> TODO()
      CreateAdminUser.Result.InvalidPassword -> TODO()
      is CreateAdminUser.Result.Success -> {
        echo("Admin user $email created, login token ${result.token}")
      }
    }
  }
}