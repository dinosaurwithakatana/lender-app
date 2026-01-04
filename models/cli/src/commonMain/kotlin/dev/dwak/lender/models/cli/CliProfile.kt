package dev.dwak.lender.models.cli

import kotlinx.serialization.Serializable

@Serializable
data class CliProfile(
  val id: String,
  val token: String,
  val email: String,
)
