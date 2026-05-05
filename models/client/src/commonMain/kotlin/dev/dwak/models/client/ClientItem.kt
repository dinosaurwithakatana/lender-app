package dev.dwak.models.client

import kotlin.jvm.JvmInline

data class ClientItem(
  val id: ClientItem.Id
) {
  @JvmInline
  value class Id(val id: String)
}
