package dev.dwak.lender.data.modifier

import de.mkammerer.argon2.Argon2
import de.mkammerer.argon2.Argon2Factory
import dev.dwak.lender.db.DbToken
import dev.dwak.lender.db.DbUser
import dev.dwak.lender.db.TokenQueries
import dev.dwak.lender.db.UserQueries
import dev.dwak.lender.lender_app.generateToken
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ClassKey
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import java.util.UUID
import kotlin.time.Clock

data class CreateUser(
    val email: String,
    val password: String,
) : DataModification<CreateUser.Result> {
    sealed interface Result : DataModification.Result {
        data class Success(val token: String) : Result
        data object InvalidPassword: Result
        data object InvalidEmail: Result
        data object MismatchedPassword: Result
    }
}

@ClassKey(CreateUser::class)
@ContributesIntoMap(scope = AppScope::class, binding = binding<DataModification.Handler<*, *>>())
@Inject
class CreateUserHandler(
    private val userQueries: UserQueries,
    private val tokenQueries: TokenQueries,
    private val passwordHasher: PasswordHasher,
) : DataModification.Handler<CreateUser.Result, CreateUser> {
    override suspend fun handle(mod: CreateUser): CreateUser.Result {
        val hashed = passwordHasher(mod.password)
        val dbUser = DbUser(
            id = UUID.randomUUID().toString(),
            email = mod.email,
            password = hashed,
            created_at = Clock.System.now().toString(),
        )
        userQueries.insert(
            dbUser = dbUser
        )
        val token = generateToken()

        tokenQueries.insertToken(
            DbToken(
                token = token,
                user_id = dbUser.id
            )
        )

        return CreateUser.Result.Success(token)
    }
}