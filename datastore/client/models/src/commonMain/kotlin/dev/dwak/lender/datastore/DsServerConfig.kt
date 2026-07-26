package dev.dwak.lender.datastore

import kotlinx.serialization.Serializable

@Serializable
data class DsServerConfig(
  val serverUrl: String? = null,
  val apiKey: String? = null,
)
