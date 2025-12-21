package dev.dwak.lender.repos.server

import dev.dwak.lender.models.server.ServerProfile

interface ProfileRepo {
    suspend fun getByEmail(email: String): ServerProfile
    suspend fun listProfiles(): List<ServerProfile>
}