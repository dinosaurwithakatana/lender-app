package dev.dwak.lender.models.api.response

import dev.dwak.lender.models.api.request.ApiLendStatus
import kotlinx.serialization.Serializable

@Serializable
data class ApiGetLendsResponse(
  val lends: List<ApiLend>,
)

@Serializable
data class ApiLend(
  val id: String,
  val itemId: String,
  val itemName: String,
  val quantity: Int,
  val status: ApiLendStatus,
  val direction: ApiLendDirection,
  val counterpartyFirstName: String,
  val counterpartyLastName: String,
  val createdAt: String,
)

@Serializable
enum class ApiLendDirection {
  OUTGOING, INCOMING
}
