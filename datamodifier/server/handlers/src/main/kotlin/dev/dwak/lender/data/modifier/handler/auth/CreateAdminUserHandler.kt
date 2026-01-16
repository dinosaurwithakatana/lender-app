package dev.dwak.lender.data.modifier.handler.auth

import dev.dwak.lender.data.modification.auth.CreateAdminUserMod
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.BoundHandler
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.data.modifier.handler.PasswordHasher
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.DbToken
import dev.dwak.lender.db.DbUser
import dev.dwak.lender.db.ProfileQueries
import dev.dwak.lender.db.TokenQueries
import dev.dwak.lender.lender_app.generateToken
import dev.zacsweers.metro.*
import java.util.*
import kotlin.time.Clock

@ContributesIntoMap(
  scope = AppScope::class,
  binding = binding<@ModificationKey(CreateAdminUserMod::class) BoundHandler>()
)
class CreateAdminUserHandler(
  private val profileQueries: ProfileQueries,
  private val tokenQueries: TokenQueries,
  private val passwordHasher: PasswordHasher,
) : DataModification.Handler<CreateAdminUserMod.Result, CreateAdminUserMod> {
  override suspend fun handle(mod: CreateAdminUserMod): CreateAdminUserMod.Result {
    val hashed = passwordHasher(mod.password)
    val userId = DbUser.Id(UUID.randomUUID().toString())
    val token = generateToken()

    profileQueries.createAdminUserWithProfile(
      user_id = userId,
      email = mod.email,
      password = hashed,
      profile_id = DbProfile.Id(UUID.randomUUID().toString()),
      first_name = mod.firstName,
      last_name = mod.lastName,
      created_at = Clock.System.now()
    )

    tokenQueries.insertToken(
      DbToken(
        token = DbToken.Token(token),
        user_id = userId,
      )
    )

    return CreateAdminUserMod.Result.Success(token)
  }
}