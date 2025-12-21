package dev.dwak.lender.repos.server

import dev.dwak.lender.db.DbToken
import dev.dwak.lender.db.DbUser
import dev.dwak.lender.db.GetUserByToken
import dev.dwak.lender.db.TokenQueries
import dev.dwak.lender.db.UserQueries
import dev.dwak.lender.models.server.ServerUser
import dev.dwak.lender.models.server.ServerUserId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlin.time.Instant

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class RealUserRepo(
    private val tokenQueries: TokenQueries,
    private val userQueries: UserQueries,
) : UserRepo {

    override suspend fun tokenExists(token: String): Boolean = tokenQueries
        .tokenExists(DbToken.Token(token))
        .executeAsOne()

    override suspend fun getUserByToken(token: String): ServerUser {
        return tokenQueries.getUserByToken(DbToken.Token(token)) { id, email, created_at ->
            ServerUser(
                id = ServerUserId(id.id),
                email = email,
                createdAt = Instant.parse(created_at)
            )
        }.executeAsOne()
    }

    override suspend fun getUserByEmail(email: String): ServerUser {
        return userQueries.findByEmail(email) { id, email, password, created_at ->
            ServerUser(
                id = ServerUserId(id.id),
                email = email,
                createdAt = Instant.parse(created_at)
            )
        }.executeAsOne()
    }

    override suspend fun getUserById(id: String): ServerUser = userQueries
        .getById(DbUser.Id(id)) { id, email, password, created_at ->
            ServerUser(
                id = ServerUserId(id.id),
                email = email,
                createdAt = Instant.parse(created_at)
            )
        }
        .executeAsOne()


    override suspend fun userExistsByEmail(email: String): Boolean {
        return userQueries.userExists(email).executeAsOne()
    }
}