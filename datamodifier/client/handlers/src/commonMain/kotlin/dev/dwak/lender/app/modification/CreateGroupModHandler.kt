package dev.dwak.lender.app.modification

import dev.dwak.lender.app.network.GroupsApi
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.models.api.request.group.ApiCreateGroupRequest
import dev.dwak.models.client.ClientGroup
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(CreateGroupMod::class)
class CreateGroupModHandler(
  private val groupsApi: GroupsApi,
) : DataModification.Handler<CreateGroupMod.Result, CreateGroupMod> {
  override suspend fun handle(mod: CreateGroupMod): CreateGroupMod.Result {
    val response = groupsApi.createGroup(
      payload = ApiCreateGroupRequest(name = mod.name)
    )

    return if (response.isSuccessful) {
      CreateGroupMod.Result.Success(ClientGroup.Id(response.body()!!.id))
    } else {
      CreateGroupMod.Result.Error
    }
  }
}
