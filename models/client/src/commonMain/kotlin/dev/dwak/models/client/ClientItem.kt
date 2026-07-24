package dev.dwak.models.client

import kotlin.jvm.JvmInline

data class ClientItem(
  val id: Id,
  val name: String,
  val description: String?,
  val totalQuantity: Int,
  val availableQuantity: Int,
  val ownedById: ClientProfile.Id,
) {
  @JvmInline
  value class Id(val id: String)
}
