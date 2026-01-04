package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.int
import dev.dwak.lender.data.modification.item.CreateItem
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.server.ServerProfileId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.binding

@ContributesIntoSet(
  scope = AppScope::class,
  binding = binding<SuspendingCliktCommand>()
)
class CreateItem(
  private val dataModifier: DataModifier,
  private val authManager: AuthManager
) : AuthCheckSuspendingCliktCommand(authManager) {

  val name by option().prompt()
  val description: String? by option().prompt()
  val quantity by option().int().prompt(default = 1)

  override suspend fun runWithAuthCheck() {
    when (val result = dataModifier.submit(
      CreateItem(
        name = name,
        description = description,
        quantity = quantity,
        ownedBy = ServerProfileId(authManager.currentProfile().id)
      )
    )) {
      is CreateItem.Result.Success -> {
        echo("Item $name created")
      }
    }
  }
}