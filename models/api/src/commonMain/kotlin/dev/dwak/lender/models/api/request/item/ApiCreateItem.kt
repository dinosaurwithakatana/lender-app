package dev.dwak.lender.models.api.request.item

import kotlinx.serialization.Serializable

@Serializable
data class ApiCreateItem(
  val name: String,
  val description: String?,
  val quantity: Int = 1,
)
