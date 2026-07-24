package dev.dwak.lender.repos.server

import dev.dwak.lender.db.DbItemLend
import dev.dwak.lender.db.DbLendStatus
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.ItemLendQueries
import dev.dwak.lender.models.server.ServerItemId
import dev.dwak.lender.models.server.ServerLend
import dev.dwak.lender.models.server.ServerLendId
import dev.dwak.lender.models.server.ServerLendStatus
import dev.dwak.lender.models.server.ServerProfileId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

@ContributesBinding(AppScope::class)
class RealLendsRepo(
  private val itemLendQueries: ItemLendQueries,
) : LendsRepo {
  override suspend fun lendsForProfile(id: ServerProfileId): List<ServerLend> {
    return itemLendQueries.listLendsForProfile(
      profile_id = DbProfile.Id(id.id),
    ) {
      lend_id,
      item_id,
      item_name,
      quantity,
      lend_status,
      from_profile_id,
      from_first_name,
      from_last_name,
      to_profile_id,
      to_first_name,
      to_last_name,
      lend_created,
      ->
      ServerLend(
        id = ServerLendId(lend_id.lend_id),
        itemId = ServerItemId(item_id.id),
        itemName = item_name,
        quantity = quantity.toInt(),
        status = lend_status.toServer(),
        fromProfileId = ServerProfileId(from_profile_id.id),
        fromFirstName = from_first_name,
        fromLastName = from_last_name,
        toProfileId = ServerProfileId(to_profile_id.id),
        toFirstName = to_first_name,
        toLastName = to_last_name,
        createdAt = lend_created,
      )
    }.executeAsList()
  }
}

private fun DbLendStatus.toServer(): ServerLendStatus = when (this) {
  DbLendStatus.REQUESTED -> ServerLendStatus.REQUESTED
  DbLendStatus.APPROVED -> ServerLendStatus.APPROVED
  DbLendStatus.DENIED -> ServerLendStatus.DENIED
  DbLendStatus.LENT -> ServerLendStatus.LENT
  DbLendStatus.RETURNED -> ServerLendStatus.RETURNED
}
