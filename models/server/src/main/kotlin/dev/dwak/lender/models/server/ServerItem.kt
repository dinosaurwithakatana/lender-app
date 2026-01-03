package dev.dwak.lender.models.server

data class ServerItem(
  val id: ServerItemId,
  val name: String,
  val description: String,
  val quantity: Int
)

@JvmInline
value class ServerItemId(val id: String)
