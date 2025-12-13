package dev.dwak.lender.lender_app.models.api.auth

import kotlinx.serialization.Serializable

@Serializable
data class ApiLoginSuccessResponse(
    val token: String,
)

typealias ApiSignupSuccessResponse = ApiLoginSuccessResponse