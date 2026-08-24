package dev.dwak.lender.app.modification

import dev.dwak.lender.app.network.MembershipsApi
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.models.api.request.membership.ApiCreateMembershipRequest
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(AddMemberMod::class)
class AddMemberModHandler(
  private val membershipsApi: MembershipsApi,
) : DataModification.Handler<AddMemberMod.Result, AddMemberMod> {
  override suspend fun handle(mod: AddMemberMod): AddMemberMod.Result {
    val response = membershipsApi.createMembership(
      payload = ApiCreateMembershipRequest(
        groupId = mod.groupId.id,
        profileId = mod.profileId.id,
      )
    )

    return if (response.isSuccessful) {
      AddMemberMod.Result.Success
    } else {
      AddMemberMod.Result.Error
    }
  }
}
