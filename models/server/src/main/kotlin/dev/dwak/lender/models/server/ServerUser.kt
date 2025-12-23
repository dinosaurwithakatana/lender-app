package dev.dwak.lender.models.server

import kotlin.time.Instant

data class ServerUser(
    val id: ServerUserId,
    val email: String,
    val createdAt: Instant
)

@JvmInline
value class ServerUserId(val id: String)
