package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import dev.dwak.lender.data.modifier.CreateUser
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.db.DbUser
import dev.dwak.lender.db.DbUserRoles
import dev.dwak.lender.db.RolesQueries
import dev.dwak.lender.db.UserQueries
import dev.dwak.lender.db.UserRolesQueries
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import java.util.UUID
import kotlin.time.Clock

@Inject
@ContributesIntoSet(AppScope::class)
class CreateAdminUser(
    private val dataModifier: DataModifier,
) : SuspendingCliktCommand("create-admin-user"){
    val email by option().prompt()
    val password by option().prompt(hideInput = true, requireConfirmation = true)

    override suspend fun run() {
        when(val result = dataModifier.submit(
            CreateUser(
                email = email,
                password = password,
                isAdmin = true
            )
        )) {
            CreateUser.Result.InvalidEmail -> {

            }
            CreateUser.Result.InvalidPassword -> {

            }
            is CreateUser.Result.Success -> {
                echo("Admin user $email created, login token ${result.token}")
            }
        }
    }
}