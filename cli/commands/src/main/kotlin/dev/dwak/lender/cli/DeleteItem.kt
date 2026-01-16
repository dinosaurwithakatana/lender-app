package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import dev.dwak.lender.data.modification.item.DeleteItemMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.server.ServerItemId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.binding

@ContributesIntoSet(
  scope = AppScope::class,
  binding = binding<SuspendingCliktCommand>()
)
class DeleteItem(
  authManager: AuthManager,
  private val dataModifier: DataModifier
): AuthCheckSuspendingCliktCommand(authManager) {
  val itemId by option().required()

  override suspend fun runWithAuthCheck() {
    when (val result = dataModifier.submit(DeleteItemMod(
      id = ServerItemId(itemId),
      ownedBy = profileId
    ))) {
      DeleteItemMod.Result.Failure,
      DeleteItemMod.Result.Unauthorized -> echo("error: $result")
      DeleteItemMod.Result.Success -> echo("success")
    }
  }
}