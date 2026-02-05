package dev.dwak.lender.data.modification.auth

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.models.server.ServerUserId

data class CreateUserMod(
  val email: String,
  val password: String,
  val firstName: String,
  val lastName: String,
  val inviteLinkToken: String? = null
) : DataModification<CreateUserMod.Result> {
  sealed interface Result : DataModification.Result {
    data class Success(val token: String, val userId: ServerUserId) : Result
    data object InvalidPassword : Result
    data object InvalidEmail : Result
    data object InvalidInviteLink : Result
  }
}