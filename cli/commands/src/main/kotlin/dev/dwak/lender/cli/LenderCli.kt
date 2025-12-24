package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand

class LenderCli : SuspendingCliktCommand() {
  override suspend fun run() = Unit
}