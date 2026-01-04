package dev.dwak.lender.cli

import dev.dwak.lender.data.modification.auth.LoginUser
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.cli.CliProfile
import dev.dwak.lender.models.server.ServerProfile
import dev.dwak.lender.repos.server.ProfileRepo
import dev.dwak.lender.repos.server.UserRepo
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.serialization.json.Json
import java.io.File

@ContributesBinding(AppScope::class)
@Inject
class RealAuthManager(
  private val appDir: File,
  private val dataModifier: DataModifier,
  private val profileRepo: ProfileRepo,
  private val userRepo: UserRepo,
) : AuthManager {
  private val loginStore by lazy { File(appDir, "profile.json") }
  override suspend fun login(
    email: String,
    password: String
  ): Result<Unit> {
    when (val result = dataModifier.submit(
      LoginUser(
        email = email,
        password = password
      )
    )) {
      is LoginUser.Result.Failure -> {
        return Result.failure(Exception("Login failure"))
      }

      is LoginUser.Result.Success -> {
        if (!loginStore.exists()) {
          loginStore.createNewFile()
        }
        val serverUser = userRepo.getUserByToken(result.token)
        val serverProfile = profileRepo.getByUserId(serverUser.id)
        val cliProfile = CliProfile(
          id = serverProfile.id.id,
          token = result.token,
          email = serverUser.email
        )
        loginStore.writeText(
          Json.encodeToString(cliProfile)
        )
        return Result.success(Unit)
      }
    }
  }

  override suspend fun logout() {
    if (loginStore.exists()) {
      loginStore.delete()
    }
  }

  override suspend fun isLoggedIn(): Boolean {
    return loginStore.exists()
  }

  override suspend fun currentProfile(): CliProfile {
    if (loginStore.exists()) {
      return Json.decodeFromString<CliProfile>(loginStore.readText())
    }
    else {
      error("Not logged in!")
    }
  }
}