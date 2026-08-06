package dev.dwak.models.client

import kotlinx.serialization.Serializable

@Serializable
data class ClientGroupDetail(
  val group: ClientGroup,
  val memberships: List<ClientMembership>,
)
