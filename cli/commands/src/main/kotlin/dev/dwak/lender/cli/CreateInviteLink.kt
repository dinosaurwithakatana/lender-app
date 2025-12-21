package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.types.int
import dev.dwak.lender.data.modification.CreateInviteLink
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.db.UserQueries
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days

@ContributesIntoSet(AppScope::class)
class CreateInviteLink(
    private val userQueries: UserQueries,
    private val dataModifier: DataModifier,
) : SuspendingCliktCommand() {
    private val name: String by argument()
    private val invitingEmail: String by argument()
    private val expirationDays: Int by argument().int()
        .default(3)

    override suspend fun run() {
        when (val result = dataModifier.submit(
            CreateInviteLink(
                name = name,
                createdByUserId = userQueries.findByEmail(invitingEmail).executeAsOne().id,
                expiresOn = Clock.System.now().plus(expirationDays.days)
            )
        )) {
            is CreateInviteLink.Result.Success -> {
                echo("Successfully created invite link: $result")
            }
        }
    }
}