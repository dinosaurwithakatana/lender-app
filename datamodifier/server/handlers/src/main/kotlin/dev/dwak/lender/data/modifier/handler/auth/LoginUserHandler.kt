package dev.dwak.lender.data.modifier.handler.auth

import dev.dwak.lender.data.modification.auth.LoginUserMod
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.BoundHandler
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.data.modifier.handler.PasswordVerifier
import dev.dwak.lender.db.DbToken
import dev.dwak.lender.db.TokenQueries
import dev.dwak.lender.db.UserQueries
import dev.dwak.lender.lender_app.generateToken
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding

@ContributesIntoMap(
  scope = AppScope::class,
  binding = binding<@ModificationKey(LoginUserMod::class) BoundHandler>()
)
class LoginUserHandler(
  private val userQueries: UserQueries,
  private val tokenQueries: TokenQueries,
  private val passwordVerifier: PasswordVerifier,
) : DataModification.Handler<LoginUserMod.Result, LoginUserMod> {
  override suspend fun handle(mod: LoginUserMod): LoginUserMod.Result {
    if (userQueries.userExists(mod.email).executeAsOne()) {
      val authToken = generateToken()

      val dbUser = userQueries.findByEmail(mod.email).executeAsOne()
      val passwordVerified = passwordVerifier(dbUser.password, mod.password)

      if (passwordVerified) {
        tokenQueries.insertToken(
          DbToken(
            token = DbToken.Token(authToken),
            user_id = dbUser.id
          )
        ).await()
        return LoginUserMod.Result.Success(authToken)
      } else {
        return LoginUserMod.Result.Failure("Incorrect password")
      }

    } else {
      return LoginUserMod.Result.Failure("User ${mod.email} not found")
    }
  }

}