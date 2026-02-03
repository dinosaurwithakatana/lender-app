package dev.dwak.lender.app.modification

import androidx.datastore.core.DataStore
import dev.dwak.lender.app.network.LoginApi
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.BoundHandler
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.datastore.DsUserInfo
import dev.dwak.lender.models.api.request.auth.ApiLoginRequest
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding

@ContributesIntoMap(
  scope = AppScope::class,
  binding = binding<@ModificationKey(LoginUser::class) BoundHandler>()
)
class LoginUserHandler(
  private val loginApi: LoginApi,
  private val dataStore: DataStore<DsUserInfo>,
) : DataModification.Handler<LoginUser.Result, LoginUser> {
  override suspend fun handle(mod: LoginUser): LoginUser.Result {
    loginApi.login(
      request = ApiLoginRequest(
        email = mod.email,
        password = mod.password
      )
    )
    return LoginUser.Result.Success
  }
}