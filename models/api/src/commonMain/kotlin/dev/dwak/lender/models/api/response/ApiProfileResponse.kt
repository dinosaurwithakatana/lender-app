package dev.dwak.lender.models.api.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiProfileResponse(
  val profileId: String,
  val firstName: String,
  val lastName: String,
)
