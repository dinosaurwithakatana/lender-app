package dev.dwak.lender.models.api.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiProfile(
  val id: String,
  val firstName: String,
  val lastName: String,
)
