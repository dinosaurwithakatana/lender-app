package dev.dwak.lender.app.modification

import androidx.datastore.core.DataStore
import dev.dwak.lender.app.network.LoginApi
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.datastore.DsUserInfo
import dev.dwak.lender.datastore.UserState
import dev.dwak.lender.models.api.request.auth.ApiSignUpRequest
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(SignUpUserMod::class)
class SignUpUserModHandler(
  private val loginApi: LoginApi,
  private val dataStore: DataStore<DsUserInfo>
) : DataModification.Handler<SignUpUserMod.Result, SignUpUserMod>{
  override suspend fun handle(mod: SignUpUserMod): SignUpUserMod.Result {
    val response = loginApi.signup(
      ApiSignUpRequest(
        email = mod.email,
        password = mod.password,
        confirmPassword = mod.confirmPassword,
        firstName = mod.firstName,
        lastName = mod.lastName,
        inviteLinkToken = mod.inviteLinkToken
      )
    )

    if (response.isSuccessful) {
      val body = requireNotNull(response.body())
      dataStore.updateData {
        it.copy(
          userState = UserState.LoggedIn(
            token = body.token,
            userId = body.userId,
            email = mod.email
          )
        )
      }
      return SignUpUserMod.Result.Success
    }
    else {
      return SignUpUserMod.Result.Error
    }
  }
}