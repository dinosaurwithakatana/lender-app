package dev.dwak.lender.data.modifier.handler.lend

import dev.dwak.lender.data.modification.lend.CreateLendMod
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.BoundHandler
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.data.modifier.handler.toDb
import dev.dwak.lender.db.DbItemLend
import dev.dwak.lender.db.ItemLendQueries
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@ContributesIntoMap(
  scope = AppScope::class,
  binding = binding<@ModificationKey(CreateLendMod::class) BoundHandler>()
)
class CreateLendHandler(
  private val itemLendQueries: ItemLendQueries,
) : DataModification.Handler<CreateLendMod.Result, CreateLendMod> {

  @OptIn(ExperimentalUuidApi::class)
  override suspend fun handle(mod: CreateLendMod): CreateLendMod.Result {
    val lendId = DbItemLend.Lend_id(Uuid.generateV4().toString())
    val lendCreated = Clock.System.now()
    itemLendQueries.insertLendRequestWithQuantity(
      lend_id = lendId,
      item_id = mod.itemId.toDb(),
      group_id = mod.groupId.toDb(),
      from_profile_id = mod.fromProfileId.toDb(),
      to_profile_id = mod.toProfileId.toDb(),
      quantity = mod.quantity.toLong(),
      lend_created = lendCreated,
      lend_updated = lendCreated,
    ).await()
    return CreateLendMod.Result.Success
  }
}