package dev.dwak.lender.cli

import dev.zacsweers.metro.createGraph
import dev.zacsweers.metro.createGraphFactory

suspend fun main(args: Array<String>) = createGraph<CliGraph>()
  .cli(args)
