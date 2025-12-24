package dev.dwak.lender.cli

import dev.zacsweers.metro.createGraphFactory

suspend fun main(args: Array<String>) = createGraphFactory<CliGraph.Factory>()
  .create(args)
  .cli()
