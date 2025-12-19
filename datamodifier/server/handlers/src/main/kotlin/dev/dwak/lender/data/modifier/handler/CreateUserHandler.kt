package dev.dwak.lender.data.modifier.handler

import dev.dwak.lender.data.modification.CreateUser
import dev.dwak.lender.data.modification.PasswordHasher
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.db.DbRoleType
import dev.dwak.lender.db.DbToken
import dev.dwak.lender.db.ProfileQueries
import dev.dwak.lender.db.RolesQueries
import dev.dwak.lender.db.TokenQueries
import dev.dwak.lender.db.UserQueries
import dev.dwak.lender.db.UserRolesQueries
import dev.dwak.lender.lender_app.generateToken
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ClassKey
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import java.util.UUID
import kotlin.time.Clock

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
            rolesQueries.getByName(DbRoleType.ADMIN).executeAsOne().id
        }
        else {
            rolesQueries.getByName(DbRoleType.GUEST).executeAsOne().id
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