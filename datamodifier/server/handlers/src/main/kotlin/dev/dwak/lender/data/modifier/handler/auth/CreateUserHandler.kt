package dev.dwak.lender.data.modifier.handler.auth

import dev.dwak.lender.data.modification.auth.CreateUserMod
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.data.modifier.handler.PasswordHasher
import dev.dwak.lender.db.DbInviteLink
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.DbToken
import dev.dwak.lender.db.DbUser
import dev.dwak.lender.db.InviteLinkQueries
import dev.dwak.lender.db.ProfileQueries
import dev.dwak.lender.db.TokenQueries
import dev.dwak.lender.lender_app.generateToken
import dev.dwak.lender.models.server.ServerUserId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import java.util.UUID
import kotlin.time.Clock

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(CreateUserMod::class)
class CreateUserHandler(
  private val inviteLinkQueries: InviteLinkQueries,
  private val profileQueries: ProfileQueries,
  private val tokenQueries: TokenQueries,
  private val passwordHasher: PasswordHasher,
) : DataModification.Handler<CreateUserMod.Result, CreateUserMod> {
  override suspend fun handle(mod: CreateUserMod): CreateUserMod.Result {

    when {
      mod.inviteLinkToken.isNullOrEmpty() -> return CreateUserMod.Result.InvalidInviteLink
      !inviteLinkQueries.linkExists(DbInviteLink.Link_token(mod.inviteLinkToken!!))
        .executeAsOne() -> return CreateUserMod.Result.InvalidInviteLink
    }

    val hashed = passwordHasher(mod.password)
    val userId = DbUser.Id(UUID.randomUUID().toString())
    val token = generateToken()

    profileQueries.createUserWithProfile(
      user_id = userId,
      email = mod.email,
      password = hashed,
      profile_id = DbProfile.Id(UUID.randomUUID().toString()),
      first_name = mod.firstName,
      last_name = mod.lastName,
      invite_token = DbInviteLink.Link_token(mod.inviteLinkToken!!),
      created_at = Clock.System.now()
    )

    tokenQueries.insertToken(
      DbToken(
        token = DbToken.Token(token),
        user_id = userId,
      )
    )

    return CreateUserMod.Result.Success(
      token = token,
      userId = ServerUserId(userId.id)
    )
  }
}