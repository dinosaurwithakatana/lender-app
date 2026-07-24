package dev.dwak.lender.data.modifier.handler.lend

import dev.dwak.lender.data.modification.lend.AdvanceLendMod
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.db.DbItemLend
import dev.dwak.lender.db.DbLendStatus
import dev.dwak.lender.db.ItemLendQueries
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import kotlin.time.Clock

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(AdvanceLendMod::class)
class AdvanceLendHandler(
  private val itemLendQueries: ItemLendQueries,
) : DataModification.Handler<AdvanceLendMod.Result, AdvanceLendMod> {
  override suspend fun handle(mod: AdvanceLendMod): AdvanceLendMod.Result {
    val lendId = DbItemLend.Lend_id(mod.lendId.id)
    val current = itemLendQueries.getLendForAction(lendId).executeAsOneOrNull()
      ?: return AdvanceLendMod.Result.NotFound

    if (mod.actorProfileId.id != current.from_profile_id.id) {
      return AdvanceLendMod.Result.Unauthorized
    }

    return when (current.lend_status) {
      DbLendStatus.REQUESTED -> {
        itemLendQueries.updateLendStatus(
          lend_status = DbLendStatus.APPROVED,
          lend_updated = Clock.System.now(),
          lend_id = lendId,
        ).await()
        AdvanceLendMod.Result.Approved
      }
      DbLendStatus.APPROVED -> {
        itemLendQueries.updateLendStatus(
          lend_status = DbLendStatus.LENT,
          lend_updated = Clock.System.now(),
          lend_id = lendId,
        ).await()
        AdvanceLendMod.Result.MarkedLent
      }
      DbLendStatus.LENT,
      DbLendStatus.DENIED,
      DbLendStatus.RETURNED -> AdvanceLendMod.Result.InvalidTransition
    }
  }
}
