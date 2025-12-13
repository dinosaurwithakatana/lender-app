package dev.dwak.lender.db

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
        properties = Properties(),
        schema = Database.Schema
    )

    @SingleIn(AppScope::class)
    @Provides
    fun db(driver: SqlDriver): Database = Database(
        driver = driver,
    )

    @SingleIn(AppScope::class)
    @Provides
    fun apiKeyQueries(db: Database): ApiKeyQueries = db.apiKeyQueries

    @SingleIn(AppScope::class)
    @Provides
    fun userQueries(db: Database): UserQueries = db.userQueries
}