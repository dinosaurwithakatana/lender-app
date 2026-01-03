package dev.dwak.lender.models.api.request.auth

import kotlinx.serialization.Serializable

@Serializable
data class ApiLoginRequest(
  val email: String,
  val password: String
)