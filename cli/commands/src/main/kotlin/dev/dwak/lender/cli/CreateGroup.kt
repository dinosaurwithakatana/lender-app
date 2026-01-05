package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import dev.dwak.lender.data.modification.group.CreateGroup
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.server.ServerProfileId
import dev.dwak.lender.repos.server.ProfileRepo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.binding

@ContributesIntoSet(
  scope = AppScope::class,
  binding = binding<SuspendingCliktCommand>()
)
class CreateGroup(
  private val authManager: AuthManager,
  private val dataModifier: DataModifier,
) : AuthCheckSuspendingCliktCommand(authManager) {
  val groupName by option().prompt()
  val forProfile by option()

  override suspend fun runWithAuthCheck() {
    dataModifier.submit(
      CreateGroup(
        name = groupName,
        owner = ServerProfileId(if (forProfile != null) forProfile!! else authManager.currentProfile().id)
      )
    )
  }
}