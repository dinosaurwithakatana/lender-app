package dev.dwak.lender.models.api.request.membership

import kotlinx.serialization.Serializable

@Serializable
data class ApiUpdateMembershipRequest(
  val status: ApiMembershipStatus,
)

@Serializable
enum class ApiMembershipStatus {
  APPROVED, REQUESTED, OWNER,
}
