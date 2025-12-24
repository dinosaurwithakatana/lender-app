package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.int
import dev.dwak.lender.data.modification.CreateInviteLink
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.repos.server.ProfileRepo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days

@ContributesIntoSet(AppScope::class)
class CreateInviteLink(
  private val profileRepo: ProfileRepo,
  private val dataModifier: DataModifier,
) : SuspendingCliktCommand() {
  private val name: String by option().prompt()
  private val invitingEmail: String by option().prompt()
  private val expirationDays: Int by option().int()
    .default(3)

  override suspend fun run() {
    when (val result = dataModifier.submit(
      CreateInviteLink(
        name = name,
        createdByProfileId = profileRepo.getByEmail(invitingEmail).id,
        expiresOn = Clock.System.now().plus(expirationDays.days)
      )
    )) {
      is CreateInviteLink.Result.Success -> {
        echo("Successfully created invite link: $result")
      }
    }
  }
}