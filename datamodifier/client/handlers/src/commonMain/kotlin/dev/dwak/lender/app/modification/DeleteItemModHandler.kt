package dev.dwak.lender.app.modification

import dev.dwak.lender.app.network.ItemApi
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(DeleteItemMod::class)
class DeleteItemModHandler(
  private val itemApi: ItemApi,
) : DataModification.Handler<DeleteItemMod.Result, DeleteItemMod> {
  override suspend fun handle(mod: DeleteItemMod): DeleteItemMod.Result {
    val response = itemApi.deleteItem(itemId = mod.itemId.id)
    return if (response.isSuccessful) DeleteItemMod.Result.Success
    else DeleteItemMod.Result.Error
  }
}
