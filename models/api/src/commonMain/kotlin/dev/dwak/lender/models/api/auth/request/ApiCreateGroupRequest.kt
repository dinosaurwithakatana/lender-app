package dev.dwak.lender.models.api.auth.request

import kotlinx.serialization.Serializable

@Serializable
data class ApiCreateGroupRequest(
  val name: String,
)