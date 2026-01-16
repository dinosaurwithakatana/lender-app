package dev.dwak.lender.data.modifier.handler.item

import dev.dwak.lender.data.modification.item.DeleteItemMod
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.BoundHandler
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.db.DbItem
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.ItemQueries
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import kotlin.uuid.ExperimentalUuidApi

@ContributesIntoMap(
  scope = AppScope::class,
  binding = binding<@ModificationKey(DeleteItemMod::class) BoundHandler>()
)
class DeleteItemHandler(
  private val itemQueries: ItemQueries,
) : DataModification.Handler<DeleteItemMod.Result, DeleteItemMod> {
  @OptIn(ExperimentalUuidApi::class)
  override suspend fun handle(mod: DeleteItemMod): DeleteItemMod.Result {
    val id = DbItem.Id(mod.id.id)
    return if (itemQueries.itemOwnedBy(
        item_id = id,
        profile_id = DbProfile.Id(mod.ownedBy.id)
      ).executeAsOne()
    ) {
      if (itemQueries.delete(id).await() > 0)
        DeleteItemMod.Result.Success
      else
        DeleteItemMod.Result.Failure
    } else {
      DeleteItemMod.Result.Unauthorized
    }
  }
}