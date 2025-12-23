package dev.dwak.lender.models.api.auth.request

import kotlinx.serialization.Serializable

@Serializable
data class ApiLoginRequest(
    val email: String,
    val password: String
)