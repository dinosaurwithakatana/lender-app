package dev.dwak.lender.app.network

data class ServerConfig(
  val serverUrl: String,
  val apiKey: String,
)

interface ServerConfigProvider {
  suspend fun current(): ServerConfig
}
