package dev.dwak.lender.models.api.request.item

import kotlinx.serialization.Serializable

@Serializable
data class ApiShareItem(
  val itemId: String,
  val groupId: String,
)