package dev.dwak.lender.repos.server

interface ApiKeyRepo {
    suspend fun hasKey(key: String): Boolean
}