package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
class ListGroups() : SuspendingCliktCommand(){
    override suspend fun run() {
        TODO("Not yet implemented")
    }

}