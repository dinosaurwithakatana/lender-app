package dev.dwak.lender.data.modifier.handler

import dev.dwak.lender.data.modification.LogoutUser
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.db.DbToken
import dev.dwak.lender.db.TokenQueries
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ClassKey
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding

@ContributesIntoMap(
  scope = AppScope::class,
  binding = binding<@ModificationKey(LogoutUser::class) BoundHandler>()
)
class LogoutUserHandler(
  private val tokenQueries: TokenQueries
) : DataModification.Handler<LogoutUser.Result, LogoutUser> {
  override suspend fun handle(mod: LogoutUser): LogoutUser.Result {
    tokenQueries.deleteToken(DbToken.Token(mod.token.token)).await()
    return LogoutUser.Result.Success
  }
}