package dev.dwak.lender.repos.server

import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.ItemQueries
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.lender.models.server.ServerItem
import dev.dwak.lender.models.server.ServerItemId
import dev.dwak.lender.models.server.ServerProfileId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@ContributesBinding(AppScope::class)
class RealItemRepo(
  private val itemQueries: ItemQueries,
  @Io private val ioDispatcher: CoroutineDispatcher,
) : ItemRepo {
  override suspend fun getItemsForProfile(id: ServerProfileId): List<ServerItem> = withContext(ioDispatcher) {
    itemQueries.itemsForProfile(DbProfile.Id(id.id)).executeAsList().map {
      ServerItem(
        id = ServerItemId(it.id.id),
        name = it.name,
        description = it.description,
        quantity = it.total_quantity.toInt()
      )
    }
  }
}