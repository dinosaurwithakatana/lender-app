package dev.dwak.lender.repos.server

interface ApiKeyRepo {
  suspend fun hasKey(key: String): Boolean
  suspend fun getKeyByName(name: String): String?
}