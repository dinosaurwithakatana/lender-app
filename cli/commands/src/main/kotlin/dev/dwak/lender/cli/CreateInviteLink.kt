package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.int
import dev.dwak.lender.data.modification.auth.CreateInviteLinkMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.repos.server.ProfileRepo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Named
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

  private val publicUrl: String by option().prompt()

  override suspend fun run() {
    when (val result = dataModifier.submit(
      CreateInviteLinkMod(
        name = name,
        createdByProfileId = profileRepo.getByEmail(invitingEmail)!!.id,
        expiresOn = Clock.System.now().plus(expirationDays.days)
      )
    )) {
      is CreateInviteLinkMod.Result.Success -> {
        echo("Invite link: $publicUrl/invite/${result.inviteLink}")
      }
    }
  }
}
