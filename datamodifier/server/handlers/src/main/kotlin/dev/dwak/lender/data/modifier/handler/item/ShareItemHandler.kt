package dev.dwak.lender.data.modifier.handler.item

import dev.dwak.lender.data.modification.item.ShareItemMod
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.BoundHandler
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.data.modifier.handler.toDb
import dev.dwak.lender.db.DbGroup
import dev.dwak.lender.db.DbItem
import dev.dwak.lender.db.DbItemGroupAccess
import dev.dwak.lender.db.GroupMembershipQueries
import dev.dwak.lender.db.GroupQueries
import dev.dwak.lender.db.ItemGroupAccessQueries
import dev.dwak.lender.db.ItemQueries
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import kotlin.time.Clock

@ContributesIntoMap(
  scope = AppScope::class,
  binding = binding<@ModificationKey(ShareItemMod::class) BoundHandler>()
)
class ShareItemHandler(
  private val itemGroupAccessQueries: ItemGroupAccessQueries,
  private val itemQueries: ItemQueries,
  private val groupQueries: GroupQueries,
  private val groupMembershipQueries: GroupMembershipQueries,
) : DataModification.Handler<ShareItemMod.Result, ShareItemMod> {
  override suspend fun handle(mod: ShareItemMod): ShareItemMod.Result {
    val itemExists = itemQueries.itemExists(mod.itemId.toDb()).executeAsOne()
    if (!itemExists) return ShareItemMod.Result.ItemNotFound

    val groupExists = groupQueries.exists(mod.groupId.toDb()).executeAsOne()
    if (!groupExists) return ShareItemMod.Result.GroupNotFound

    val groupMembershipExists = groupMembershipQueries.isMemberOfGroup(
      profile_id = mod.profileId.toDb(),
      group_id = mod.groupId.toDb()
    ).executeAsOne()

    if (!groupMembershipExists) return ShareItemMod.Result.Unauthorized

    val isItemOwnedByOwner = itemQueries.itemOwnedBy(
      item_id = mod.itemId.toDb(),
      profile_id = mod.profileId.toDb()
    ).executeAsOne()

    if (!isItemOwnedByOwner) return ShareItemMod.Result.Unauthorized
    val rowsUpdated = itemGroupAccessQueries.insert(
      DbItemGroupAccess(
        item_id = DbItem.Id(mod.itemId.id),
        group_id = DbGroup.Id(mod.groupId.id),
        granted_at = Clock.System.now()
      )
    ).await()

    return if (rowsUpdated == 0L) {
      ShareItemMod.Result.UnknownError
    } else {
      ShareItemMod.Result.Success
    }
  }
}