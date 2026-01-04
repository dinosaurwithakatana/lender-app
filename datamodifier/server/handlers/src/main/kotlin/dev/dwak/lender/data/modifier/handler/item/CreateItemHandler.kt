package dev.dwak.lender.data.modifier.handler.item

import dev.dwak.lender.data.modification.item.CreateItem
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.BoundHandler
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.db.DbItem
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.ItemQueries
import dev.dwak.lender.models.server.ServerItemId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@ContributesIntoMap(
  scope = AppScope::class,
  binding = binding<@ModificationKey(CreateItem::class) BoundHandler>()
)
class CreateItemHandler(
    private val itemQueries: ItemQueries,
) : DataModification.Handler<CreateItem.Result, CreateItem> {
  @OptIn(ExperimentalUuidApi::class)
  override suspend fun handle(mod: CreateItem): CreateItem.Result {
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

    return CreateItem.Result.Success(ServerItemId(itemID.id))
  }
}