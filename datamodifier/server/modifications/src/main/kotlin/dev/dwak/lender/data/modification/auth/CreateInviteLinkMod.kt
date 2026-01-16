package dev.dwak.lender.data.modification.auth

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.models.server.ServerProfileId
import kotlin.time.Instant

data class CreateInviteLinkMod(
  val name: String,
  val createdByProfileId: ServerProfileId,
  val expiresOn: Instant,
) : DataModification<CreateInviteLinkMod.Result> {
  sealed interface Result : DataModification.Result {
    data class Success(val inviteLink: String) : Result
  }
}

