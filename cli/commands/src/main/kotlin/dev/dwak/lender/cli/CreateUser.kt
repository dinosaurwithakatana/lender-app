package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import dev.dwak.lender.data.modification.auth.CreateUserMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
class CreateUser(
  private val dataModifier: DataModifier,
) : SuspendingCliktCommand() {
  val inviteLinkToken by argument()
  val email by option().prompt()
  val password by option().prompt(hideInput = true, requireConfirmation = true)
  val firstName by option().prompt(default = "Regular")
  val lastName by option().prompt(default = "User")

  override suspend fun run() {
    when (val result = dataModifier.submit(
      CreateUserMod(
        email = email,
        password = password,
        firstName = firstName,
        lastName = lastName,
        inviteLinkToken = inviteLinkToken,
      )
    )) {
      CreateUserMod.Result.InvalidEmail -> TODO()
      CreateUserMod.Result.InvalidInviteLink -> {
        echo("Invalid invite link")
      }

      CreateUserMod.Result.InvalidPassword -> TODO()
      is CreateUserMod.Result.Success -> {
        echo("Admin user $email created, login token ${result.token}")

      }
    }
  }
}