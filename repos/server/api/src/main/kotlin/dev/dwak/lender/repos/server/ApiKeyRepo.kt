package dev.dwak.lender.repos.server

import dev.dwak.lender.models.server.ServerApiKey

interface ApiKeyRepo {
  suspend fun hasKey(key: String): Boolean
  suspend fun getKeyByName(name: String): String?

  suspend fun getAllKeys(): List<ServerApiKey>
}