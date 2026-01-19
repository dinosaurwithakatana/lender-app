package dev.dwak.lender.repos.server

import dev.dwak.lender.db.DbItem
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.GroupMembershipQueries
import dev.dwak.lender.db.GroupQueries
import dev.dwak.lender.db.ItemGroupAccessQueries
import dev.dwak.lender.models.server.ServerGroup
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerItemId
import dev.dwak.lender.models.server.ServerProfileId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

@ContributesBinding(AppScope::class)
class RealGroupsRepo(
  private val groupsQueries: GroupQueries,
  private val groupMembershipQueries: GroupMembershipQueries,
  private val itemGroupAccessQueries: ItemGroupAccessQueries,
) : GroupsRepo {
  override suspend fun listAll(): List<ServerGroup> {
    return groupsQueries.selectAll { id, name, created_at ->
      ServerGroup(
        id = ServerGroupId(id.id),
        name = name,
        createdAt = created_at,
      )
    }.executeAsList()
  }

  override suspend fun groupsForProfile(id: ServerProfileId): List<ServerGroup> {
    return groupMembershipQueries.groupsForProfile(DbProfile.Id(id.id)) { id, name, created_at ->
      ServerGroup(
        id = ServerGroupId(id.id),
        name = name,
        createdAt = created_at,
      )

    }.executeAsList()
  }

  override suspend fun groupsForItem(id: ServerItemId): List<ServerGroup> {
    return itemGroupAccessQueries.getGroupsForItem(DbItem.Id(id.id)) { group_id, name, created_at, granted_at ->
      ServerGroup(
        id = ServerGroupId(group_id.id),
        name = name,
        createdAt = created_at
      )
    }.executeAsList()
  }
}