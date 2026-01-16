package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.int
import dev.dwak.lender.data.modification.item.CreateItemMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.binding

@ContributesIntoSet(
  scope = AppScope::class,
  binding = binding<SuspendingCliktCommand>()
)
class CreateItem(
  private val dataModifier: DataModifier,
  authManager: AuthManager
) : AuthCheckSuspendingCliktCommand(authManager) {

  val name by option().prompt()
  val description: String? by option().prompt()
  val quantity by option().int().prompt(default = 1)

  override suspend fun runWithAuthCheck() {
    when (val result = dataModifier.submit(
      CreateItemMod(
        name = name,
        description = description,
        quantity = quantity,
        ownedBy = profileId
      )
    )) {
      is CreateItemMod.Result.Success -> {
        echo("Item $name created")
      }
    }
  }
}