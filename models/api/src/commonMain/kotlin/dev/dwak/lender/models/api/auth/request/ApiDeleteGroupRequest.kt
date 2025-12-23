package dev.dwak.lender.models.api.auth.request

import kotlinx.serialization.Serializable

@Serializable
data class ApiDeleteGroupRequest(
    val id: String,
)