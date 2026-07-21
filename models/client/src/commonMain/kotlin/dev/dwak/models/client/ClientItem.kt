package dev.dwak.models.client

import kotlin.jvm.JvmInline

data class ClientItem(
  val id: ClientItem.Id,
  val name: String,
  val description: String?,
  val quantity: Int,
  val ownedById: ClientProfile.Id
) {
  @JvmInline
  value class Id(val id: String)
}
