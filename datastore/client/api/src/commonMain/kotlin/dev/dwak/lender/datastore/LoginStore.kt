package dev.dwak.lender.datastore

import kotlinx.serialization.Serializable

@Serializable
data class LoginStore(
  val id: String,
  val token: String,
  val email: String,
)