package dev.dwak.lender.app.modification

import dev.dwak.lender.app.network.ItemApi
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.models.api.request.item.ApiCreateItemRequest
import dev.dwak.models.client.ClientItem
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(CreateItemMod::class)
class CreateItemModHandler(
  private val itemApi: ItemApi,
) : DataModification.Handler<CreateItemMod.Result, CreateItemMod>{
  override suspend fun handle(mod: CreateItemMod): CreateItemMod.Result {
    val response = itemApi.createItem(
      payload = ApiCreateItemRequest(
        name = mod.name,
        description =  mod.description,
        quantity = mod.quantity
      )
    )

    if (response.isSuccessful) {
      return CreateItemMod.Result.Success(ClientItem.Id(response.body()!!.id))
    }
    else {
      return CreateItemMod.Result.Error
    }
  }
}