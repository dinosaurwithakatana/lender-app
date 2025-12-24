package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import dev.dwak.lender.repos.server.ProfileRepo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
class ListProfiles(
  private val profileRepo: ProfileRepo
) : SuspendingCliktCommand() {
  override suspend fun run() {
    val profiles = profileRepo.listProfiles()

    echo(profiles.joinToString("\n"))
  }
}