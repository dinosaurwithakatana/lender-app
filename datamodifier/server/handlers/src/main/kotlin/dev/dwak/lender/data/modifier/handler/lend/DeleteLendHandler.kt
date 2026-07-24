package dev.dwak.lender.data.modifier.handler.lend

import dev.dwak.lender.data.modification.lend.DeleteLendMod
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.db.DbItemLend
import dev.dwak.lender.db.DbLendStatus
import dev.dwak.lender.db.ItemLendQueries
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import kotlin.time.Clock

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(DeleteLendMod::class)
class DeleteLendHandler(
  private val itemLendQueries: ItemLendQueries,
) : DataModification.Handler<DeleteLendMod.Result, DeleteLendMod> {
  override suspend fun handle(mod: DeleteLendMod): DeleteLendMod.Result {
    val lendId = DbItemLend.Lend_id(mod.lendId.id)
    val current = itemLendQueries.getLendForAction(lendId).executeAsOneOrNull()
      ?: return DeleteLendMod.Result.NotFound

    val actorId = mod.actorProfileId.id
    if (actorId != current.from_profile_id.id && actorId != current.to_profile_id.id) {
      return DeleteLendMod.Result.Unauthorized
    }

    return when (current.lend_status) {
      DbLendStatus.REQUESTED, DbLendStatus.APPROVED -> {
        itemLendQueries.updateLendStatus(
          lend_status = DbLendStatus.DENIED,
          lend_updated = Clock.System.now(),
          lend_id = lendId,
        ).await()
        DeleteLendMod.Result.Denied
      }
      DbLendStatus.LENT -> {
        itemLendQueries.updateLendStatus(
          lend_status = DbLendStatus.RETURNED,
          lend_updated = Clock.System.now(),
          lend_id = lendId,
        ).await()
        DeleteLendMod.Result.Returned
      }
      DbLendStatus.DENIED, DbLendStatus.RETURNED -> {
        itemLendQueries.deleteLend(lendId).await()
        DeleteLendMod.Result.Deleted
      }
    }
  }
}
