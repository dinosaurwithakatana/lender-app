package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import dev.dwak.lender.data.modification.item.ShareItem
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerItemId
import dev.dwak.lender.models.server.ServerProfileId
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
      ShareItem(
        itemId = ServerItemId(itemId),
        groupId = ServerGroupId(groupId),
        profileId = profileId
      )
    )) {
      ShareItem.Result.GroupNotFound,
      ShareItem.Result.ItemNotFound,
      ShareItem.Result.Unauthorized,
      ShareItem.Result.UnknownError -> {
        echo("error $result")
      }

      ShareItem.Result.Success -> echo("Successfully shared!")
    }
  }
}