package dev.dwak.lender.cli

import androidx.datastore.core.DataStore
import dev.dwak.lender.data.modification.auth.LoginUserMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.datastore.DsUserInfo
import dev.dwak.lender.datastore.UserState
import dev.dwak.lender.lender_app.AppDir
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.lender.models.cli.CliProfile
import dev.dwak.lender.repos.server.ProfileRepo
import dev.dwak.lender.repos.server.UserRepo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.io.files.Path
import kotlinx.serialization.json.Json
import java.io.File

@ContributesBinding(AppScope::class)
@Inject
class RealAuthManager(
  private val datastore: DataStore<DsUserInfo>,
  private val dataModifier: DataModifier,
  private val profileRepo: ProfileRepo,
  private val userRepo: UserRepo,
  @Io private val ioDispatcher: CoroutineDispatcher
) : AuthManager {
  override suspend fun login(
    email: String,
    password: String
  ): Result<Unit> = withContext(ioDispatcher) {
    when (val result = dataModifier.submit(
      LoginUserMod(
        email = email,
        password = password
      )
    )) {
      is LoginUserMod.Result.Failure -> {
        Result.failure(Exception("Login failure"))
      }

      is LoginUserMod.Result.Success -> {
        val serverUser = userRepo.getUserByToken(result.token)
        val serverProfile = profileRepo.getByUserId(serverUser.id)
        val cliProfile = CliProfile(
          id = serverProfile!!.id.id,
          token = result.token,
          email = serverUser.email
        )
        datastore.updateData {
          it.copy(
            token = UserState.LoggedIn(
              token = cliProfile.token,
              userId = cliProfile.id,
              email = cliProfile.email
            )
          )
        }
        Result.success(Unit)
      }
    }
  }

  override suspend fun logout() {
    datastore.updateData {
      it.copy(
        token = UserState.LoggedOut,
      )
    }
  }

  override suspend fun isLoggedIn(): Boolean {
    return datastore.data.first().token is UserState.LoggedIn
  }

  override suspend fun currentProfile(): CliProfile = withContext(ioDispatcher) {
    datastore.data.filterIsInstance<UserState.LoggedIn>().map {
      CliProfile(
        id = it.userId,
        token = it.token,
        email = it.email
      )
    }.first()
  }
}