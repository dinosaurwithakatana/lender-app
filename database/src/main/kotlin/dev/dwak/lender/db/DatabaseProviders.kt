package dev.dwak.lender.db

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.dwak.lender.lender_app.AppDir
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import java.io.File
import java.util.Properties
import kotlin.time.Instant

@ContributesTo(AppScope::class)
interface DatabaseProviders {

  @SingleIn(AppScope::class)
  @Provides
  fun driver(
    @AppDir dir: Path
  ): SqlDriver {
    val appPath = SystemFileSystem.resolve(dir)
    return JdbcSqliteDriver(
      url = "jdbc:sqlite:${appPath}/data.db",
      properties = Properties().apply { put("foreign_keys", "true") },
      schema = Database.Schema
    )
  }

  @SingleIn(AppScope::class)
  @Provides
  fun instantAdapter(): ColumnAdapter<Instant, String> = object : ColumnAdapter<Instant, String> {
    override fun decode(databaseValue: String): Instant = Instant.parse(databaseValue)
    override fun encode(value: Instant): String = value.toString()
  }

  @SingleIn(AppScope::class)
  @Provides
  fun db(driver: SqlDriver, instantAdapter: ColumnAdapter<Instant, String>): Database = Database(
    driver = driver,
    dbGroupMembershipAdapter = DbGroupMembership.Adapter(
      statusAdapter = EnumColumnAdapter(),
      created_atAdapter = instantAdapter
    ),
    dbRolesAdapter = DbRoles.Adapter(EnumColumnAdapter()),
    dbInviteLinkAdapter = DbInviteLink.Adapter(expires_atAdapter = instantAdapter),
    dbItemAdapter = DbItem.Adapter(created_atAdapter = instantAdapter),
    dbUserAdapter = DbUser.Adapter(created_atAdapter = instantAdapter),
    dbGroupAdapter = DbGroup.Adapter(created_atAdapter = instantAdapter),
    dbItemLendAdapter = DbItemLend.Adapter(
      lend_createdAdapter = instantAdapter,
      lend_updatedAdapter = instantAdapter(),
      lend_statusAdapter = EnumColumnAdapter()
    ),
    dbItemGroupAccessAdapter = DbItemGroupAccess.Adapter(
      granted_atAdapter = instantAdapter
    )
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

  @SingleIn(AppScope::class)
  @Provides
  fun inviteHistoryQueries(db: Database): InviteHistoryQueries = db.inviteHistoryQueries

  @SingleIn(AppScope::class)
  @Provides
  fun groupQueries(db: Database): GroupQueries = db.groupQueries

  @SingleIn(AppScope::class)
  @Provides
  fun groupMembershipQueries(db: Database): GroupMembershipQueries = db.groupMembershipQueries

  @SingleIn(AppScope::class)
  @Provides
  fun itemQueries(db: Database): ItemQueries = db.itemQueries

  @SingleIn(AppScope::class)
  @Provides
  fun itemGroupAccessQueries(db: Database): ItemGroupAccessQueries = db.itemGroupAccessQueries

  @SingleIn(AppScope::class)
  @Provides
  fun itemLendQueries(db: Database): ItemLendQueries = db.itemLendQueries
}