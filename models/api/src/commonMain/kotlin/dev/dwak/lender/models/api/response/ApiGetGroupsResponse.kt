package dev.dwak.lender.models.api.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiGetGroupsResponse(
  val groups: List<ApiGroup>
)

@Serializable
data class ApiGroup(
  val id: String,
  val name: String,
  val createdAt: String,
)
