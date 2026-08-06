package dev.dwak.models.client

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
data class ClientProfile(
  val id: Id,
  val firstName: String,
  val lastName: String,
) {
  @Serializable
  @JvmInline
  value class Id(val id: String)
}
