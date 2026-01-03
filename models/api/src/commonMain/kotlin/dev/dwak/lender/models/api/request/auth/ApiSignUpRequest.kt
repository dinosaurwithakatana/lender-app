package dev.dwak.lender.models.api.request.auth

import kotlinx.serialization.Serializable

@Serializable
data class ApiSignUpRequest(
  val email: String,
  val password: String,
  val confirmPassword: String,
  val firstName: String,
  val lastName: String,
  val inviteLinkToken: String? = null
)
