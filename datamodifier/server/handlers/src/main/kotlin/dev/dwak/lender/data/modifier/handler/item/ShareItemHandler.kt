package dev.dwak.lender.data.modifier.handler.item

import dev.dwak.lender.data.modification.item.ShareItem
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
  binding = binding<@ModificationKey(ShareItem::class) BoundHandler>()
)
class ShareItemHandler(
  private val itemGroupAccessQueries: ItemGroupAccessQueries,
  private val itemQueries: ItemQueries,
  private val groupQueries: GroupQueries,
  private val groupMembershipQueries: GroupMembershipQueries,
) : DataModification.Handler<ShareItem.Result, ShareItem> {
  override suspend fun handle(mod: ShareItem): ShareItem.Result {
    val itemExists = itemQueries.itemExists(mod.itemId.toDb()).executeAsOne()
    if (!itemExists) return ShareItem.Result.ItemNotFound

    val groupExists = groupQueries.exists(mod.groupId.toDb()).executeAsOne()
    if (!groupExists) return ShareItem.Result.GroupNotFound

    val groupMembershipExists = groupMembershipQueries.isMemberOfGroup(
      profile_id = mod.profileId.toDb(),
      group_id = mod.groupId.toDb()
    ).executeAsOne()

    if (!groupMembershipExists) return ShareItem.Result.Unauthorized

    val isItemOwnedByOwner = itemQueries.itemOwnedBy(
      item_id = mod.itemId.toDb(),
      profile_id = mod.profileId.toDb()
    ).executeAsOne()

    if (!isItemOwnedByOwner) return ShareItem.Result.Unauthorized
    val rowsUpdated = itemGroupAccessQueries.insert(
      DbItemGroupAccess(
        item_id = DbItem.Id(mod.itemId.id),
        group_id = DbGroup.Id(mod.groupId.id),
        granted_at = Clock.System.now()
      )
    ).await()

    return if (rowsUpdated == 0L) {
      ShareItem.Result.UnknownError
    } else {
      ShareItem.Result.Success
    }
  }
}