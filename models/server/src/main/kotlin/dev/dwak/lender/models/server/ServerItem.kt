package dev.dwak.lender.models.server

data class ServerItem(
  val id: ServerItemId,
  val name: String,
  val description: String?,
  val quantity: Int,
  val ownedBy: ServerProfileId
)

@JvmInline
value class ServerItemId(val id: String)
