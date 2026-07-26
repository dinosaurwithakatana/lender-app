package dev.dwak.lender.models.server

import kotlin.time.Instant

data class ServerGroupMembership(
  val id: ServerGroupMembershipId,
  val profileId: ServerProfileId,
  val groupId: ServerGroupId,
  val status: ServerGroupMembershipStatus,
  val createdAt: Instant,
)

@JvmInline
value class ServerGroupMembershipId(
  val id: String
)

data class ServerGroupMembershipWithProfile(
  val membership: ServerGroupMembership,
  val profile: ServerProfile,
)
