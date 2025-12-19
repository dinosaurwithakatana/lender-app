package dev.dwak.lender.data.modification

import dev.dwak.lender.data.modifier.DataModification
import kotlin.time.Instant

data class CreateInviteLink(
    val name: String,
    val createdByUserId: String,
    val expiresOn: Instant,
): DataModification<CreateInviteLink.Result> {
    sealed interface Result: DataModification.Result {
        data class Success(val inviteLink: String): Result
    }
}

