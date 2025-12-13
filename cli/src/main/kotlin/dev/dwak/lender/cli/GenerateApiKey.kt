package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import dev.dwak.lender.db.ApiKeyQueries
import dev.dwak.lender.db.DbApiKey
import dev.dwak.lender.lender_app.generateToken

class GenerateApiKey(
    private val apiKeyQueries: ApiKeyQueries,
) : SuspendingCliktCommand() {
    override suspend fun run() {
        val newKey = generateToken()

        apiKeyQueries.insertKey(DbApiKey(
            apiKey = newKey
        ))

        echo("Successfully created API key $newKey")
    }
}