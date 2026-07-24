package dev.dwak.models.client

import kotlin.jvm.JvmInline

data class ClientGroup(
  val id: ClientGroup.Id,
  val name: String,
) {
  @JvmInline
  value class Id(val id: String)
}
