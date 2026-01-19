package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.core.terminal
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.mordant.input.interactiveSelectList
import dev.dwak.lender.data.modification.item.ShareItemMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerItemId
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
class ShareItem(
  authManager: AuthManager,
  private val dataModifier: DataModifier,
  private val groupsRepo: GroupsRepo,
  private val itemsRepo: ItemRepo,
) : AuthCheckSuspendingCliktCommand(authManager) {

  override suspend fun run(profileId: ServerProfileId) {
    val items = itemsRepo.getItemsForProfile(profileId)
    val selectedItemName = terminal.interactiveSelectList(
      entries = items.map { it.rendered() },
      title = "Select an item"
    )
    val selectedItem = items.find { it.rendered() == selectedItemName }
    val groups = groupsRepo.groupsForProfile(profileId)
    val selectedGroupName =
      terminal.interactiveSelectList(
        entries = groups.map { it.rendered() },
        title = "Select a group to share with"
      )
    val selectedGroup = groups.find { it.rendered() == selectedGroupName }

    when (val result = dataModifier.submit(
      ShareItemMod(
        itemId = selectedItem!!.id,
        groupId = selectedGroup!!.id,
        profileId = profileId
      )
    )) {
      ShareItemMod.Result.GroupNotFound,
      ShareItemMod.Result.ItemNotFound,
      ShareItemMod.Result.Unauthorized,
      ShareItemMod.Result.UnknownError -> {
        echo("error $result")
      }

      ShareItemMod.Result.Success -> echo("Successfully shared!")
    }
  }
}