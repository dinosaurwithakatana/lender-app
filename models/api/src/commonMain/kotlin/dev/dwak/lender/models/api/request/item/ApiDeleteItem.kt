package dev.dwak.lender.models.api.request.item

import kotlinx.serialization.Serializable

@Serializable
data class ApiDeleteItem(
  val id: String,
)
