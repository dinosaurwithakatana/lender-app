package dev.dwak.lender.repos.client

import dev.dwak.lender.app.network.ItemApi
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.models.client.ClientItem
import dev.dwak.models.client.ClientProfile
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

@ContributesBinding(scope = AppScope::class, binding = binding<ItemRepo>())
@ContributesBinding(
  scope = AppScope::class,
  binding = binding<RepoRefresher<ItemRepo.RefreshTypes>>()
)
@SingleIn(AppScope::class)
class RealItemRepo(
  private val itemsApi: ItemApi,
  @Io private val dispatcher: CoroutineDispatcher,
) : ItemRepo, RepoRefresher<ItemRepo.RefreshTypes> {
  override val items: Flow<List<ClientItem>>
    field = MutableStateFlow(listOf())

  override suspend fun refresh(item: ItemRepo.RefreshTypes): Unit = withContext(dispatcher) {
    when (item) {
      ItemRepo.RefreshTypes.AllItems -> {
        Napier.d { "All items refreshing" }
        val response = itemsApi.getCurrentUserItems()
        Napier.v { "Current user items refreshing" }
        items.value = if (response.isSuccessful) {
          response.body()?.items?.map {
            ClientItem(
              id = ClientItem.Id(it.id),
              name = it.name,
              description = it.description,
              totalQuantity = it.totalQuantity,
              availableQuantity = it.availableQuantity,
              ownedById = ClientProfile.Id(it.ownedById),
            )
          } ?: emptyList()
        } else {
          emptyList()
        }
      }
    }
  }
}