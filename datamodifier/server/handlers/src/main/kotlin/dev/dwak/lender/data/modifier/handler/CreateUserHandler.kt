package dev.dwak.lender.data.modifier.handler

import dev.dwak.lender.data.modification.CreateUser
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.db.DbInviteLink
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.DbToken
import dev.dwak.lender.db.DbUser
import dev.dwak.lender.db.InviteLinkQueries
import dev.dwak.lender.db.ProfileQueries
import dev.dwak.lender.db.TokenQueries
import dev.dwak.lender.lender_app.generateToken
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import java.util.*
import kotlin.time.Clock

@ContributesIntoMap(
    scope = AppScope::class,
    binding = binding<@ModificationKey(CreateUser::class) BoundHandler>()
)
class CreateUserHandler(
    private val inviteLinkQueries: InviteLinkQueries,
    private val profileQueries: ProfileQueries,
    private val tokenQueries: TokenQueries,
    private val passwordHasher: PasswordHasher,
) : DataModification.Handler<CreateUser.Result, CreateUser> {
    override suspend fun handle(mod: CreateUser): CreateUser.Result {

        when {
            mod.inviteLinkToken.isNullOrEmpty() -> return CreateUser.Result.InvalidInviteLink
            !inviteLinkQueries.linkExists(DbInviteLink.Link_token(mod.inviteLinkToken!!))
                .executeAsOne() -> return CreateUser.Result.InvalidInviteLink
        }

        val hashed = passwordHasher(mod.password)
        val userId = DbUser.Id(UUID.randomUUID().toString())
        val token = generateToken()

        profileQueries.createUserWithProfile(
            user_id = userId,
            email = mod.email,
            password = hashed,
            created_at = Clock.System.now().toString(),
            profile_id = DbProfile.Id(UUID.randomUUID().toString()),
            first_name = mod.firstName,
            last_name = mod.lastName,
            invite_token = DbInviteLink.Link_token(mod.inviteLinkToken!!)
        )

        tokenQueries.insertToken(
            DbToken(
                token = DbToken.Token(token),
                user_id = userId,
            )
        )

        return CreateUser.Result.Success(token)
    }
}