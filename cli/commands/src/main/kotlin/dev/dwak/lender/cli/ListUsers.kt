package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import dev.dwak.lender.repos.server.UserRepo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
class ListUsers(
  private val userRepo: UserRepo,
) : SuspendingCliktCommand() {
  override suspend fun run() {
    val users = userRepo.listAll()
    echo(users.joinToString("\n"))
  }
}