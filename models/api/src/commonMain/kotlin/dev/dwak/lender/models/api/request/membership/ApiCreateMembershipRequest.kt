package dev.dwak.lender.models.api.request.membership

import kotlinx.serialization.Serializable

@Serializable
data class ApiCreateMembershipRequest(
  val groupId: String,
)
