package dev.dwak.lender.data.modifier.handler

import dev.dwak.lender.data.modification.CreateUser
import dev.dwak.lender.data.modification.LoginUser
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.db.DbToken
import dev.dwak.lender.db.TokenQueries
import dev.dwak.lender.db.UserQueries
import dev.dwak.lender.lender_app.generateToken
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding

@ContributesIntoMap(
    scope = AppScope::class,
    binding = binding<@ModificationKey(LoginUser::class) BoundHandler>()
)
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
                        token = DbToken.Token(authToken),
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