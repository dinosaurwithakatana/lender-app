package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject

@ContributesIntoSet(AppScope::class)
class Init() : SuspendingCliktCommand(){
    override suspend fun run() = Unit
}