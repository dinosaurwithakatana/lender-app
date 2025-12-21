package dev.dwak.lender.models.server

data class ServerGroup(
    val id: ServerGroupId,
    val name: String
)

@JvmInline
value class ServerGroupId(val id: String)
