package dev.dwak.lender.models.api.response

import kotlinx.serialization.Serializable

@Serializable
data class ApiLoginSuccessResponse(
  val userId: String,
  val token: String,
)

typealias ApiSignupSuccessResponse = ApiLoginSuccessResponse