package dev.dwak.models.client

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
data class ClientMembership(
  val id: Id,
  val groupId: ClientGroup.Id,
  val status: ClientMembershipStatus,
  val profile: ClientProfile,
) {
  @Serializable
  @JvmInline
  value class Id(val id: String)
}

enum class ClientMembershipStatus {
  APPROVED,
  REQUESTED,
  OWNER,
}
