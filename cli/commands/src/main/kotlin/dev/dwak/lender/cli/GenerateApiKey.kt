package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import dev.dwak.lender.db.ApiKeyQueries
import dev.dwak.lender.db.DbApiKey
import dev.dwak.lender.lender_app.generateToken
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject

@Inject
@ContributesIntoSet(AppScope::class)
class GenerateApiKey(
    private val apiKeyQueries: ApiKeyQueries,
) : SuspendingCliktCommand() {
    val name by argument()

    override suspend fun run() {
        val newKey = generateToken()

        apiKeyQueries.insertKey(DbApiKey(
            apiKey = newKey,
            name = name
        ))

        echo("Successfully created API key $newKey")
    }
}