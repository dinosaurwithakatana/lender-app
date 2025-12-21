package dev.dwak.lender.models.server

data class ServerProfile(
    val id: ServerProfileId,
)

@JvmInline
value class ServerProfileId(
    val id: String
)