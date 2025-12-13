package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.main
import dev.zacsweers.metro.createGraph

suspend fun main(args: Array<String>) {
    val graph = createGraph<CliGraph>()
    GenerateApiKey(graph.apiKeyQueries)
        .main(args)
}