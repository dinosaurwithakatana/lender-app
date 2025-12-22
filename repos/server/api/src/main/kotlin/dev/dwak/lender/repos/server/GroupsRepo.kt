package dev.dwak.lender.repos.server

import dev.dwak.lender.models.server.ServerGroup

interface GroupsRepo {
    suspend fun listAll(): List<ServerGroup>
}