package dev.dwak.models.client

import kotlin.jvm.JvmInline

data class ClientProfile(
  val id: Id,
  val firstName: String,
  val lastName: String,
) {
  @JvmInline
  value class Id(val id: String)
}
