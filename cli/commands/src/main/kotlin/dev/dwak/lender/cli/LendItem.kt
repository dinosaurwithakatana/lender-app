package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.core.terminal
import com.github.ajalt.mordant.input.interactiveSelectList
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.server.ServerGroup
import dev.dwak.lender.models.server.ServerItem
import dev.dwak.lender.models.server.ServerProfile
import dev.dwak.lender.models.server.ServerProfileId
import dev.dwak.lender.repos.server.GroupsRepo
import dev.dwak.lender.repos.server.ItemRepo
import dev.dwak.lender.repos.server.ProfileRepo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.binding

@ContributesIntoSet(
  scope = AppScope::class,
  binding = binding<SuspendingCliktCommand>()
)
class LendItem(
  authManager: AuthManager,
  private val dataModifier: DataModifier,
  private val groupsRepo: GroupsRepo,
  private val itemsRepo: ItemRepo,
  private val profileRepo: ProfileRepo,
) : AuthCheckSuspendingCliktCommand(authManager) {

  override suspend fun run(profileId: ServerProfileId) {
    val items = itemsRepo.getItemsForProfile(profileId)
    val groups = groupsRepo.groupsForProfile(profileId)
    val selectedItemName = terminal.interactiveSelectList(
      entries = items.map { it.rendered() },
      title = "Select an item"
    )
    val selectedItem = items.find { it.rendered() == selectedItemName }
    val selectedGroupName =
      terminal.interactiveSelectList(
        entries = groups.map { it.rendered() },
        title = "Select a group to share with"
      )
    val selectedGroup = groups.find { it.rendered() == selectedGroupName }

    val profilesInGroup = profileRepo.getProfilesInGroup(selectedGroup!!.id)
    val selectedShareProfile =
      terminal.interactiveSelectList(
        entries = profilesInGroup.map { it.rendered() },
        title = "Select a profile to share with"
      )

    val selectedProfile = profilesInGroup.find { it.rendered() == selectedShareProfile }

    echo("Selected: $selectedItem to share with $selectedProfile in $selectedGroup")
  }

  private fun ServerProfile.rendered(): String = "[${id.id}] $firstName $lastName"

  private fun ServerItem.rendered(): String = "[${id.id}] $name"

  private fun ServerGroup.rendered(): String = "[${id.id}] $name"
}