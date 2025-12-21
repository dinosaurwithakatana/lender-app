package dev.dwak.lender.data.modifier.handler

import dev.dwak.lender.data.modification.CreateAdminUser
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.db.DbToken
import dev.dwak.lender.db.ProfileQueries
import dev.dwak.lender.db.TokenQueries
import dev.dwak.lender.lender_app.generateToken
import dev.zacsweers.metro.*
import java.util.*
import kotlin.time.Clock

@ModificationKey(CreateAdminUser::class)
@ContributesIntoMap(scope = AppScope::class, binding = binding<DataModification.Handler<*, *>>())
@Inject
class CreateAdminUserHandler(
    private val profileQueries: ProfileQueries,
    private val tokenQueries: TokenQueries,
    private val passwordHasher: PasswordHasher,
) : DataModification.Handler<CreateAdminUser.Result, CreateAdminUser> {
    override suspend fun handle(mod: CreateAdminUser): CreateAdminUser.Result {
        val hashed = passwordHasher(mod.password)
        val userId = UUID.randomUUID().toString()
        val token = generateToken()

        profileQueries.createAdminUserWithProfile(
            user_id = userId,
            email = mod.email,
            password = hashed,
            created_at = Clock.System.now().toString(),
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

        return CreateAdminUser.Result.Success(token)
    }
}