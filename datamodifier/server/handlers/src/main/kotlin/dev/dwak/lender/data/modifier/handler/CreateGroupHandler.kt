package dev.dwak.lender.data.modifier.handler

import dev.dwak.lender.data.modification.CreateGroup
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.db.DbGroup
import dev.dwak.lender.db.DbGroupMembership
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.GroupQueries
import dev.dwak.lender.models.server.ServerGroupId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import java.util.*
import kotlin.time.Clock

@ContributesIntoMap(
  scope = AppScope::class,
  binding = binding<@ModificationKey(CreateGroup::class) BoundHandler>()
)
class CreateGroupHandler(
  private val groupQueries: GroupQueries
) : DataModification.Handler<CreateGroup.Result, CreateGroup> {
  override suspend fun handle(mod: CreateGroup): CreateGroup.Result {
    val groupId = DbGroup.Id(UUID.randomUUID().toString())
    groupQueries.createGroupAndAddCreator(
      group_id = groupId,
      group_name = mod.name,
      membership_id = DbGroupMembership.Id(UUID.randomUUID().toString()),
      profile_id = DbProfile.Id(mod.owner.id),
      created_at = Clock.System.now()
    )

    return CreateGroup.Result.Success(ServerGroupId(id = groupId.id))
  }
}