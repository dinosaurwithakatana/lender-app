package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import dev.dwak.lender.db.ApiKeyQueries
import dev.dwak.lender.db.DbApiKey
import kotlin.random.Random

class GenerateApiKey(
    private val apiKeyQueries: ApiKeyQueries,
) : SuspendingCliktCommand() {
    override suspend fun run() {
        val newKey = CharArray(KEY_LENGTH) {
            CHARS[Random.nextInt(CHARS.length)]
        }.concatToString()

        apiKeyQueries.insertKey(DbApiKey(
            apiKey = newKey
        ))

        echo("Successfully created API key $newKey")
    }

    companion object {
        private const val CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        private const val KEY_LENGTH = 24
    }
}