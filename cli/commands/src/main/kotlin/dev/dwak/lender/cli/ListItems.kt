package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import dev.dwak.lender.models.server.ServerProfileId
import dev.dwak.lender.repos.server.ItemRepo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.binding

@ContributesIntoSet(
  scope = AppScope::class,
  binding = binding<SuspendingCliktCommand>()
)
class ListItems(
  private val authManager: AuthManager,
  private val itemRepo: ItemRepo,
) : AuthCheckSuspendingCliktCommand(authManager){
  override suspend fun runWithAuthCheck() {
    echo(itemRepo.getItemsForProfile(ServerProfileId(authManager.currentProfile().id)).joinToString("\n"))
  }
}