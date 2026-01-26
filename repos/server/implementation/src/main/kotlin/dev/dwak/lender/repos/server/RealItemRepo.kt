package dev.dwak.lender.repos.server

import dev.dwak.lender.db.DbItem
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
import kotlin.time.Instant

@ContributesBinding(AppScope::class)
class RealItemRepo(
  private val itemQueries: ItemQueries,
  @Io private val ioDispatcher: CoroutineDispatcher,
) : ItemRepo {
  override suspend fun getItemsForProfile(id: ServerProfileId): List<ServerItem> =
    withContext(ioDispatcher) {
      itemQueries.itemsForProfile(
        owned_by_profile_id = DbProfile.Id(id.id),
        mapper = dbToServer()
      ).executeAsList()
    }

  override suspend fun getItemById(id: ServerItemId): ServerItem? {
    return itemQueries.select(
      item_id = DbItem.Id(id.id),
      mapper = dbToServer()
    ).executeAsOneOrNull()
  }

  private fun dbToServer(): (DbItem.Id, String, DbProfile.Id, Instant, String?, Long) -> ServerItem =
    { id, name, owned_by_profile_id, created_at, description, total_quantity ->
      ServerItem(
        id = ServerItemId(id.id),
        name = name,
        description = description,
        quantity = total_quantity.toInt(),
        ownedBy = ServerProfileId(owned_by_profile_id.id)
      )
    }
}