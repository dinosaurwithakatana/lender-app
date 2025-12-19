package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.command.main
import com.github.ajalt.clikt.core.subcommands
import dev.dwak.lender.lender_app.coroutines.Io
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@DependencyGraph(AppScope::class)
interface CliGraph {
    val cli: suspend () -> Unit

    @Provides
    fun lenderCli(
        subCommands: Set<SuspendingCliktCommand>,
        args: Array<String>
    ) : suspend () -> Unit = {
        LenderCli()
            .subcommands(subCommands)
            .main(args)
    }

    @Provides @Io
    fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(@Provides args: Array<String>): CliGraph
    }
}