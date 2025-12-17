package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.main
import com.github.ajalt.clikt.core.subcommands
import dev.zacsweers.metro.createGraph

suspend fun main(args: Array<String>) {
    val graph = createGraph<CliGraph>()
    LenderCli()
        .subcommands(GenerateApiKey(graph.apiKeyQueries))
        .main(args)
}