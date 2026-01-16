package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import dev.dwak.lender.data.modification.item.ShareItemMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerItemId
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
) : AuthCheckSuspendingCliktCommand(authManager) {
  val itemId by option().required()
  val groupId by option().required()

  override suspend fun runWithAuthCheck() {
    when (val result = dataModifier.submit(
      ShareItemMod(
        itemId = ServerItemId(itemId),
        groupId = ServerGroupId(groupId),
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