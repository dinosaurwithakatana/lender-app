package dev.dwak.lender.models.server

data class ServerProfile(
  val id: ServerProfileId,
  val firstName: String,
  val lastName: String,
)

@JvmInline
value class ServerProfileId(
  val id: String
)