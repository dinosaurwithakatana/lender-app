package dev.dwak.lender.models.api.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiServerInfoResponse(
  val name: String,
  val version: String,
)
