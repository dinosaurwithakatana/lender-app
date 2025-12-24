package dev.dwak.lender.models.server

data class UserIdToken(
  val userId: ServerUserId,
  val token: ServerToken
)