package dev.dwak.lender.app.network

interface AuthPluginConfig {
  suspend fun token(): String?
}

annotation class AuthRequired