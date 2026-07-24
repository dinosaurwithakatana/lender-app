package dev.dwak.lender.models.server

import kotlin.jvm.JvmInline
import kotlin.time.Instant

data class ServerLend(
  val id: ServerLendId,
  val itemId: ServerItemId,
  val itemName: String,
  val quantity: Int,
  val status: ServerLendStatus,
  val fromProfileId: ServerProfileId,
  val fromFirstName: String,
  val fromLastName: String,
  val toProfileId: ServerProfileId,
  val toFirstName: String,
  val toLastName: String,
  val createdAt: Instant,
)

@JvmInline
value class ServerLendId(val id: String)
