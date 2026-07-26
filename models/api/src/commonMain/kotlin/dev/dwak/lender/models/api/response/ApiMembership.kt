package dev.dwak.lender.models.api.response

import dev.dwak.lender.models.api.request.membership.ApiMembershipStatus
import kotlinx.serialization.Serializable

@Serializable
data class ApiMembership(
  val id: String,
  val groupId: String,
  val status: ApiMembershipStatus,
  val profile: ApiProfile,
  val createdAt: String,
)

@Serializable
data class ApiGetMembershipsResponse(
  val memberships: List<ApiMembership>,
)

@Serializable
data class ApiCreateMembershipResponse(
  val id: String,
)
