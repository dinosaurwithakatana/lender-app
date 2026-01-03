package dev.dwak.lender.data.modifier.handler.item

import dev.dwak.lender.data.modification.item.CreateItem
import dev.dwak.lender.data.modification.item.DeleteItem
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
  binding = binding<@ModificationKey(DeleteItem::class) BoundHandler>()
)
class DeleteItemHandler(
  private val itemQueries: ItemQueries,
) : DataModification.Handler<DeleteItem.Result, DeleteItem> {
  @OptIn(ExperimentalUuidApi::class)
  override suspend fun handle(mod: DeleteItem): DeleteItem.Result {
    val id = DbItem.Id(mod.id.id)
    return if (itemQueries.itemOwnedBy(
        item_id = id,
        profile_id = DbProfile.Id(mod.ownedBy.id)
      ).executeAsOne()
    ) {
      if (itemQueries.delete(id).await() > 0)
        DeleteItem.Result.Success
      else
        DeleteItem.Result.Failure
    } else {
      DeleteItem.Result.Unauthorized
    }
  }
}