package dev.dwak.lender.app.modification

import dev.dwak.lender.app.network.MembershipsApi
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.models.api.request.membership.ApiMembershipStatus
import dev.dwak.lender.models.api.request.membership.ApiUpdateMembershipRequest
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(ApproveGroupMembershipMod::class)
class ApproveGroupMembershipModHandler(
  private val membershipsApi: MembershipsApi
): DataModification.Handler<ApproveGroupMembershipMod.Result, ApproveGroupMembershipMod> {
  override suspend fun handle(mod: ApproveGroupMembershipMod): ApproveGroupMembershipMod.Result {
    val response = membershipsApi.updateMembership(
      id = mod.groupId.id,
      payload = ApiUpdateMembershipRequest(ApiMembershipStatus.APPROVED)
    )
    return if (response.isSuccessful) {
      ApproveGroupMembershipMod.Result.Success
    } else {
      ApproveGroupMembershipMod.Result.Failure
    }
  }
}