package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.core.terminal
import com.github.ajalt.mordant.input.interactiveSelectList
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.server.ServerProfileId
import dev.dwak.lender.repos.server.GroupsRepo
import dev.dwak.lender.repos.server.ItemRepo
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
) : AuthCheckSuspendingCliktCommand(authManager) {

  override suspend fun run(profileId: ServerProfileId) {
    val items = itemsRepo.getItemsForProfile(profileId)
    val groups = groupsRepo.groupsForProfile(profileId)
    val selectedItemName = terminal.interactiveSelectList(items.map { it.name }, "Select an item")
    val selectedGroupName = terminal.interactiveSelectList(groups.map { it.name}, title = "Select a group to share with")
  }
}