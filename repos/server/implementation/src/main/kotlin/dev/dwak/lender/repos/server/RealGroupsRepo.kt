package dev.dwak.lender.repos.server

import dev.dwak.lender.db.GroupQueries
import dev.dwak.lender.models.server.ServerGroup
import dev.dwak.lender.models.server.ServerGroupId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

@ContributesBinding(AppScope::class)
class RealGroupsRepo(
    private val groupsQueries: GroupQueries
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
}