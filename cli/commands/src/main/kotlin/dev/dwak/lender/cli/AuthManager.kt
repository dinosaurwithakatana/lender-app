package dev.dwak.lender.cli

import dev.dwak.lender.models.cli.CliProfile

interface AuthManager {
  suspend fun login(email: String, password: String): Result<Unit>
  suspend fun logout()
  suspend fun isLoggedIn(): Boolean
  suspend fun currentProfile(): CliProfile
}