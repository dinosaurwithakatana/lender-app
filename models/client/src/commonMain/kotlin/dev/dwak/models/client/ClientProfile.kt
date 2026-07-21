package dev.dwak.models.client

import kotlin.jvm.JvmInline

data class ClientProfile(
  val id: ClientProfile.Id
) {
  @JvmInline
  value class Id(val id: String)
}