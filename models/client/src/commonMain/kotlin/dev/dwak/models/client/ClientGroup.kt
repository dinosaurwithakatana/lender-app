package dev.dwak.models.client

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
data class ClientGroup(
  val id: ClientGroup.Id,
  val name: String,
) {
  @Serializable
  @JvmInline
  value class Id(val id: String)
}
