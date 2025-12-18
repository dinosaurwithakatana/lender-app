package dev.dwak.lender.data.modifier

import dev.dwak.lender.db.DbToken
import dev.dwak.lender.db.TokenQueries
import dev.dwak.lender.db.UserQueries
import dev.dwak.lender.lender_app.generateToken
import dev.dwak.lender.models.server.ServerUser
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ClassKey
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding

data class LoginUser(
    val email: String,
    val password: String
) : DataModification<LoginUser.Result> {
    sealed interface Result : DataModification.Result {
        data class Success(val token: String) : Result
        data class Failure(val error: String) : Result
    }
}

@ClassKey(LoginUser::class)
@ContributesIntoMap(scope = AppScope::class, binding = binding<DataModification.Handler<*, *>>())
@Inject
class LoginUserHandler(
    private val userQueries: UserQueries,
    private val tokenQueries: TokenQueries,
    private val passwordVerifier: PasswordVerifier,
) : DataModification.Handler<LoginUser.Result, LoginUser> {
    override suspend fun handle(mod: LoginUser): LoginUser.Result {
        if (userQueries.userExists(mod.email).executeAsOne()) {
            val authToken = generateToken()

            val dbUser = userQueries.findByEmail(mod.email).executeAsOne()
            val passwordVerified = passwordVerifier(dbUser.password, mod.password)

            if (passwordVerified) {
                tokenQueries.insertToken(
                    DbToken(
                        token = authToken,
                        user_id = dbUser.id
                    )
                ).await()
                return LoginUser.Result.Success(authToken)
            } else {
                return LoginUser.Result.Failure("Incorrect password")
            }

        } else {
            return LoginUser.Result.Failure("User ${mod.email} not found")
        }
    }

}
