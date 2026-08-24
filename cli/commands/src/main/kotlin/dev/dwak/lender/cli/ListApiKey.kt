package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import dev.dwak.lender.models.server.ServerProfileId
import dev.dwak.lender.repos.server.ApiKeyRepo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.binding

@ContributesIntoSet(
  scope = AppScope::class,
)
class ListApiKeys(
  private val apiKeyRepo: ApiKeyRepo
): SuspendingCliktCommand() {
  override suspend fun run() {
    val message = apiKeyRepo.getAllKeys().joinToString("\n")
    echo(message)
  }
}