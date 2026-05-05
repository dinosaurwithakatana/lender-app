package dev.dwak.lender.app.modification

import androidx.datastore.core.DataStore
import dev.dwak.lender.app.network.LoginApi
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.datastore.DsUserInfo
import dev.dwak.lender.datastore.UserState
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(LogoutMod::class)
class LogoutHandler(
  private val loginApi: LoginApi,
  private val dataStore: DataStore<DsUserInfo>
): DataModification.Handler<LogoutMod.Result, LogoutMod> {
  override suspend fun handle(mod: LogoutMod): LogoutMod.Result {
    val response = loginApi.logout()

    if (response.isSuccessful) {
      dataStore.updateData {
        it.copy(
          userState = UserState.LoggedOut
        )
      }
      return LogoutMod.Result.Success
    }
    else {
      error("error in logout")
    }
  }
}