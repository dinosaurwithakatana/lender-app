package dev.dwak.lender.server.common

import dev.dwak.lender.models.api.request.membership.ApiMembershipStatus
import dev.dwak.lender.models.api.response.ApiMembership
import dev.dwak.lender.models.api.response.ApiProfile
import dev.dwak.lender.models.server.ServerGroupMembershipStatus
import dev.dwak.lender.models.server.ServerGroupMembershipWithProfile

fun ServerGroupMembershipWithProfile.toApiMembership(): ApiMembership = ApiMembership(
    id = membership.id.id,
    groupId = membership.groupId.id,
    status = membership.status.toApi(),
    profile = ApiProfile(
        id = profile.id.id,
        firstName = profile.firstName,
        lastName = profile.lastName,
    ),
    createdAt = membership.createdAt.toString(),
)

fun ServerGroupMembershipStatus.toApi(): ApiMembershipStatus = when (this) {
  ServerGroupMembershipStatus.APPROVED -> ApiMembershipStatus.APPROVED
  ServerGroupMembershipStatus.REQUESTED -> ApiMembershipStatus.REQUESTED
  ServerGroupMembershipStatus.OWNER -> ApiMembershipStatus.OWNER
}
