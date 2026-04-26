package dev.dwak.lender.data.modifier.handler.lend

import dev.dwak.lender.data.modification.lend.CreateGuestLendMod
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.data.modifier.handler.toDb
import dev.dwak.lender.db.DbItemLend
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.ItemLendQueries
import dev.dwak.lender.models.server.ServerProfile
import dev.dwak.lender.models.server.ServerProfileId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import java.util.UUID
import kotlin.time.Clock

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(CreateGuestLendMod::class)
class CreateGuestLendHandler(
  private val itemLendQueries: ItemLendQueries,
) : DataModification.Handler<CreateGuestLendMod.Result, CreateGuestLendMod> {
  override suspend fun handle(mod: CreateGuestLendMod): CreateGuestLendMod.Result {
    val canLend = itemLendQueries.canLendQuantity(
      item_id = mod.itemId.toDb(),
      requested_quantity = mod.quantity.toDouble(),
    ).executeAsOneOrNull()

    if (canLend != true) return CreateGuestLendMod.Result.InsufficientQuantity

    val profileId = DbProfile.Id(UUID.randomUUID().toString())
    val now = Clock.System.now()
    itemLendQueries.createGuestProfileAndLend(
      profile_id = profileId,
      first_name = mod.firstName,
      last_name = mod.lastName,
      lend_id = DbItemLend.Lend_id(UUID.randomUUID().toString()),
      item_id = mod.itemId.toDb(),
      from_profile_id = mod.fromProfileId.toDb(),
      quantity = mod.quantity.toLong(),
      lend_created = now,
    )

    return CreateGuestLendMod.Result.Success(
      guestProfile = ServerProfile(
        id = ServerProfileId(profileId.id),
        firstName = mod.firstName,
        lastName = mod.lastName,
      )
    )
  }
}
