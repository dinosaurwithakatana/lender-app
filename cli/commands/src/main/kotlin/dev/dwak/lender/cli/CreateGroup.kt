package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import dev.dwak.lender.data.modification.group.CreateGroupMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.server.ServerProfileId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.binding

@ContributesIntoSet(
  scope = AppScope::class,
  binding = binding<SuspendingCliktCommand>()
)
class CreateGroup(
  authManager: AuthManager,
  private val dataModifier: DataModifier,
) : AuthCheckSuspendingCliktCommand(authManager) {
  val groupName by option().prompt()

  override suspend fun run(profileId: ServerProfileId) {
    dataModifier.submit(
      CreateGroupMod(
        name = groupName,
        owner = profileId
      )
    )
  }
}