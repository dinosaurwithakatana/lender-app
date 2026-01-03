package dev.dwak.lender.models.api.request.group

import kotlinx.serialization.Serializable

@Serializable
data class ApiDeleteGroupRequest(
  val id: String,
)