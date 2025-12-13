package dev.dwak.lender.lender_app.models.api.auth

import kotlinx.serialization.Serializable

@Serializable
data class ApiSignUpRequest(
    val email: String,
    val password: String,
    val confirmPassword: String,
)
