package dev.dwak.lender.lender_app.repo

import dev.dwak.lender.db.GetUserByToken
import dev.dwak.lender.db.TokenQueries
import dev.dwak.lender.db.UserQueries
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@SingleIn(AppScope::class)
@Inject
class UserRepo(
    private val tokenQueries: TokenQueries,
    private val userQueries: UserQueries,
) {

    suspend fun tokenExists(token: String) = tokenQueries.tokenExists(token).executeAsOne()

    suspend fun getUserByToken(token: String): GetUserByToken {
        return tokenQueries.getUserByToken(token).executeAsOne()
    }

    suspend fun getUserById(id: String) = userQueries.getById(id).executeAsOne()

}