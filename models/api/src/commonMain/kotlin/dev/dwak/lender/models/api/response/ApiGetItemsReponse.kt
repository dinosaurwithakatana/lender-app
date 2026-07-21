package dev.dwak.lender.models.api.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiGetItemsReponse(
  val items: List<ApiItem>
)

@Serializable
data class ApiItem(
  val id: String,
  val name: String,
  val description: String?,
  val quantity: Int,
  val ownedById: String
)