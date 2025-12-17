package dev.dwak.lender.data.modifier

import dev.dwak.lender.db.DbToken
import dev.dwak.lender.db.DbUser
import dev.dwak.lender.db.DbUserRoles
import dev.dwak.lender.db.RolesQueries
import dev.dwak.lender.db.TokenQueries
import dev.dwak.lender.db.UserQueries
import dev.dwak.lender.db.UserRolesQueries
import dev.dwak.lender.lender_app.generateToken
import dev.zacsweers.metro.*
import java.util.*
import kotlin.time.Clock

data class CreateUser(
    val email: String,
    val password: String,
    val isAdmin: Boolean = false,
) : DataModification<CreateUser.Result> {
    sealed interface Result : DataModification.Result {
        data class Success(val token: String) : Result
        data object InvalidPassword: Result
        data object InvalidEmail: Result
    }
}

@ClassKey(CreateUser::class)
@ContributesIntoMap(scope = AppScope::class, binding = binding<DataModification.Handler<*, *>>())
@Inject
class CreateUserHandler(
    private val userQueries: UserQueries,
    private val tokenQueries: TokenQueries,
    private val userRolesQueries: UserRolesQueries,
    private val rolesQueries: RolesQueries,
    private val passwordHasher: PasswordHasher,
) : DataModification.Handler<CreateUser.Result, CreateUser> {
    override suspend fun handle(mod: CreateUser): CreateUser.Result {
        val hashed = passwordHasher(mod.password)
        val userId = UUID.randomUUID().toString()
        val dbUser = DbUser(
            id = userId,
            email = mod.email,
            password = hashed,
            created_at = Clock.System.now().toString(),
        )
        userQueries.insert(
            dbUser = dbUser
        )

        if (mod.isAdmin) {
            val adminRole = rolesQueries.getByName("admin").executeAsOne()
            val dbRole = DbUserRoles(
                user_id = userId,
                role_id = adminRole.id
            )
            userRolesQueries.insertUserRole(dbRole)
        }

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