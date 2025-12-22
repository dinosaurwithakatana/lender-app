package dev.dwak.lender.models.server

import kotlin.time.Instant

data class ServerGroup(
    val id: ServerGroupId,
    val name: String,
    val createdAt: Instant,
)

@JvmInline
value class ServerGroupId(val id: String)
