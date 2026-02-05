package dev.dwak.lender.repos.client

import androidx.datastore.core.DataStore
import dev.dwak.lender.datastore.DsUserInfo
import dev.dwak.lender.datastore.UserState
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.models.client.ClientUser
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn

@ContributesBinding(scope = AppScope::class)
class RealUserRepo(
  @Io private val ioDispatcher: CoroutineDispatcher,
  @Io private val ioScope: CoroutineScope,
  private val datastore: DataStore<DsUserInfo>
) : UserRepo {
  override fun currentUser(): StateFlow<ClientUser> {
    return datastore.data
      .map { it.userState }
      .map {
        when (it) {
          is UserState.LoggedIn -> {
            ClientUser.LoggedIn(
              id = ClientUser.LoggedIn.Id(it.userId),
              token = it.token,
              email = it.email
            )
          }

          UserState.LoggedOut -> ClientUser.LoggedOut
        }
      }.stateIn(
        scope = ioScope,
        started = SharingStarted.Eagerly,
        initialValue = ClientUser.LoggedOut
      )
  }
}