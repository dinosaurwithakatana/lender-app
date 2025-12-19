package dev.dwak.lender.db

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import java.util.Properties

@ContributesTo(AppScope::class)
interface DatabaseProviders {

    @SingleIn(AppScope::class)
    @Provides
    fun driver(): SqlDriver = JdbcSqliteDriver(
        url = "jdbc:sqlite:/tmp/data.db",
        properties = Properties().apply { put("foreign_keys", "true") },
        schema = Database.Schema
    )

    @SingleIn(AppScope::class)
    @Provides
    fun db(driver: SqlDriver): Database = Database(
        driver = driver,
        dbRolesAdapter = DbRoles.Adapter(EnumColumnAdapter())
    )

    @SingleIn(AppScope::class)
    @Provides
    fun apiKeyQueries(db: Database): ApiKeyQueries = db.apiKeyQueries

    @SingleIn(AppScope::class)
    @Provides
    fun userQueries(db: Database): UserQueries = db.userQueries

    @SingleIn(AppScope::class)
    @Provides
    fun tokenQueries(db: Database): TokenQueries = db.tokenQueries

    @SingleIn(AppScope::class)
    @Provides
    fun rolesQueries(db: Database): RolesQueries = db.rolesQueries

    @SingleIn(AppScope::class)
    @Provides
    fun userRolesQueries(db: Database): UserRolesQueries = db.userRolesQueries

    @SingleIn(AppScope::class)
    @Provides
    fun profileRolesQueries(db: Database): ProfileQueries = db.profileQueries

    @SingleIn(AppScope::class)
    @Provides
    fun inviteLinkQueries(db: Database): InviteLinkQueries = db.inviteLinkQueries
}