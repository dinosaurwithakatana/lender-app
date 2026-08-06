package dev.dwak.lender.models.api.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiGetGroupDetailResponse(
  val group: ApiGroup,
  val memberships: List<ApiMembership>
)
