package dev.dwak.lender.repos.client

import dev.dwak.lender.app.network.ItemApi
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.models.client.ClientItem
import dev.dwak.models.client.ClientProfile
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@ContributesBinding(scope = AppScope::class)
@SingleIn(AppScope::class)
class RealItemRepo(
  private val itemsApi: ItemApi,
  @Io private val dispatcher: CoroutineDispatcher,
): ItemRepo {
  override suspend fun items(): List<ClientItem>  = withContext(dispatcher){
    val response = itemsApi.getCurrentUserItems()
    if (response.isSuccessful) {
      response.body()?.items?.map {
        ClientItem(
          id = ClientItem.Id(it.id),
          name = it.name,
          description = it.description,
          quantity = it.quantity,
          ownedById = ClientProfile.Id(it.id),
        )
      }?: emptyList()
    }
    else {
      emptyList()
    }
  }
}