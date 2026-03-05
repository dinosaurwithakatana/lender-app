package dev.dwak.lender.app.modification

import androidx.datastore.core.DataStore
import dev.dwak.lender.app.network.AuthApi
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.BoundHandler
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.datastore.DsUserInfo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding

@ContributesIntoMap(
  scope = AppScope::class,
  binding = binding<@ModificationKey(LogoutUser::class) BoundHandler>()
)
class LogoutHandler(
  private val authApi: AuthApi,
  private val dataStore: DataStore<DsUserInfo>,
) : DataModification.Handler<LogoutUser.Success, LogoutUser> {
  override suspend fun handle(mod: LogoutUser): LogoutUser.Success {
    TODO("Not yet implemented")
  }
}