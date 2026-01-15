package dev.dwak.lender.models.api.request

data class ApiCreateLend(
  val itemId: String,
  val groupId: String,
  val toProfileId: String,
  val lendStatus: ApiLendStatus,
  val quantity: Int
)

enum class ApiLendStatus {
  REQUESTED, APPROVED, DENIED, LENT, RETURNED
}