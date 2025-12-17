package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.core.CliktCommand

class LenderCli : SuspendingCliktCommand("lender-cli") {
    override suspend fun run() = Unit
}