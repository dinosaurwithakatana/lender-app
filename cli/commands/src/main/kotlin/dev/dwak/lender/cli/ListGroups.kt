package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import dev.dwak.lender.repos.server.GroupsRepo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
class ListGroups(
    private val groupsRepo: GroupsRepo,
) : SuspendingCliktCommand() {
    override suspend fun run() {
        echo(groupsRepo.listAll().joinToString("\n"))
    }
}