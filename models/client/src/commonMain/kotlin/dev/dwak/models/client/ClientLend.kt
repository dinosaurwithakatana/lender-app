package dev.dwak.models.client

import kotlin.jvm.JvmInline

data class ClientLend(
  val id: Id,
  val itemName: String,
  val quantity: Int,
  val status: ClientLendStatus,
  val direction: Direction,
  val counterpartyFirstName: String,
  val counterpartyLastName: String,
  val createdAt: String,
) {
  @JvmInline
  value class Id(val id: String)

  enum class Direction {
    OUTGOING, INCOMING
  }
}

enum class ClientLendStatus {
  REQUESTED, APPROVED, DENIED, LENT, RETURNED
}
