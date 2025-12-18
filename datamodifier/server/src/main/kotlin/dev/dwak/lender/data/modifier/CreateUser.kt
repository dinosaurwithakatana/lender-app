package dev.dwak.lender.data.modifier

import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.DbToken
import dev.dwak.lender.db.DbUser
import dev.dwak.lender.db.DbUserRoles
import dev.dwak.lender.db.ProfileQueries
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
    val firstName: String,
    val lastName: String,
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
    private val profileQueries: ProfileQueries,
    private val tokenQueries: TokenQueries,
    private val userRolesQueries: UserRolesQueries,
    private val rolesQueries: RolesQueries,
    private val passwordHasher: PasswordHasher,
) : DataModification.Handler<CreateUser.Result, CreateUser> {
    override suspend fun handle(mod: CreateUser): CreateUser.Result {
        val hashed = passwordHasher(mod.password)
        val userId = UUID.randomUUID().toString()
        val token = generateToken()

        val roleId = if (mod.isAdmin) {
            rolesQueries.getByName("admin").executeAsOne().id
        }
        else {
            rolesQueries.getByName("user").executeAsOne().id
        }

        profileQueries.createUserWithProfile(
            user_id = userId,
            email = mod.email,
            password = hashed,
            created_at = Clock.System.now().toString(),
            role_id = roleId,
            profile_id = UUID.randomUUID().toString(),
            first_name = mod.firstName,
            last_name = mod.lastName,
        )


        tokenQueries.insertToken(
            DbToken(
                token = token,
                user_id = userId,
            )
        )

        return CreateUser.Result.Success(token)
    }
}