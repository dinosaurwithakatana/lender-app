package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import dev.dwak.lender.data.modification.CreateGroup
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.repos.server.ProfileRepo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
class CreateGroup(
  private val dataModifier: DataModifier,
  private val profileRepo: ProfileRepo
) : SuspendingCliktCommand() {
  val groupName by option().prompt()
  val ownerEmail by option().prompt()

  override suspend fun run() {
    dataModifier.submit(
      CreateGroup(
        name = groupName,
        owner = profileRepo.getByEmail(ownerEmail).id
      )
    )
  }
}