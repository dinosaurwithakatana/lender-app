package dev.dwak.lender.lender_app.models.api.auth

import kotlinx.serialization.Serializable

@Serializable
data class ApiLoginRequest(
    val email: String,
    val password: String
)