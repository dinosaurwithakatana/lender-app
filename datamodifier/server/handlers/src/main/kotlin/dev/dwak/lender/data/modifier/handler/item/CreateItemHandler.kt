package dev.dwak.lender.data.modifier.handler.item

import dev.dwak.lender.data.modification.item.CreateItemMod
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.db.DbItem
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.ItemQueries
import dev.dwak.lender.models.server.ServerItemId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(CreateItemMod::class)
class CreateItemHandler(
  private val itemQueries: ItemQueries,
) : DataModification.Handler<CreateItemMod.Result, CreateItemMod> {
  @OptIn(ExperimentalUuidApi::class)
  override suspend fun handle(mod: CreateItemMod): CreateItemMod.Result {
    val itemID = DbItem.Id(Uuid.generateV4().toString())
    itemQueries.insert(
      dbItem = DbItem(
        id = itemID,
        name = mod.name,
        owned_by_profile_id = DbProfile.Id(mod.ownedBy.id),
        created_at = Clock.System.now(),
        description = mod.description,
        total_quantity = mod.quantity.toLong()
      )
    ).await()

    return CreateItemMod.Result.Success(ServerItemId(itemID.id))
  }
}