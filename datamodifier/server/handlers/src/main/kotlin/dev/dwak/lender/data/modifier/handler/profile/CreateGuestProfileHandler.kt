package dev.dwak.lender.data.modifier.handler.profile

import dev.dwak.lender.data.modification.profile.CreateGuestProfileMod
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.ProfileQueries
import dev.dwak.lender.models.server.ServerProfile
import dev.dwak.lender.models.server.ServerProfileId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import java.util.UUID

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(CreateGuestProfileMod::class)
class CreateGuestProfileHandler(
  private val profileQueries: ProfileQueries,
) : DataModification.Handler<CreateGuestProfileMod.Result, CreateGuestProfileMod> {
  override suspend fun handle(mod: CreateGuestProfileMod): CreateGuestProfileMod.Result {
    val profileId = DbProfile.Id(UUID.randomUUID().toString())
    profileQueries.createGuestProfile(
      id = profileId,
      first_name = mod.firstName,
      last_name = mod.lastName,
    )
    return CreateGuestProfileMod.Result.Success(
      profile = ServerProfile(
        id = ServerProfileId(profileId.id),
        firstName = mod.firstName,
        lastName = mod.lastName,
      )
    )
  }
}
