package dev.dwak.lender.app.network

sealed interface ServerValidationResult {
  data object Valid : ServerValidationResult
  data object InvalidApiKey : ServerValidationResult
  data class Unreachable(val message: String) : ServerValidationResult
}

interface ServerConfigValidator {
  suspend fun validate(serverUrl: String, apiKey: String): ServerValidationResult
}
