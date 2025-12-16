package dev.dwak.lender.models.server

import kotlin.time.Instant

data class ServerUser(
    val id: String,
    val email: String,
    val createdAt: Instant
)
