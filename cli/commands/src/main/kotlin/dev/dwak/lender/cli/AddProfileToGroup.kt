package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.core.terminal
import com.github.ajalt.mordant.input.interactiveSelectList
import dev.dwak.lender.models.server.ServerProfileId
import dev.dwak.lender.repos.server.GroupsRepo
import dev.dwak.lender.repos.server.ProfileRepo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.binding

@ContributesIntoSet(
  scope = AppScope::class,
  binding = binding<SuspendingCliktCommand>()
)
class AddProfileToGroup(
  authManager: AuthManager,
  private val profileRepo: ProfileRepo,
  private val groupsRepo: GroupsRepo,
) : AuthCheckSuspendingCliktCommand(authManager) {
  override suspend fun run(profileId: ServerProfileId) {
    val profiles = profileRepo.listProfiles().filter { it.id != profileId }
    val selectedProfileName = terminal.interactiveSelectList(
      entries = profiles.map { it.rendered() },
      title = "Select a profile"
    )
    val selectedProfile = profiles.find { it.rendered() == selectedProfileName }
  }
}