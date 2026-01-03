package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import dev.dwak.lender.data.modification.auth.CreateApiKey
import dev.dwak.lender.data.modifier.DataModifier
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
class GenerateApiKey(
  private val dataModifier: DataModifier,
) : SuspendingCliktCommand() {
  val name by argument()

  override suspend fun run() {
    when (val result = dataModifier.submit(CreateApiKey(name = name))) {
      is CreateApiKey.Result.Success -> echo("Successfully created API key ${result.apiKey}")
    }
  }
}