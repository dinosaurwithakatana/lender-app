package dev.dwak.lender.data.modification

import dev.dwak.lender.data.modifier.DataModification

data class CreateUser(
  val email: String,
  val password: String,
  val firstName: String,
  val lastName: String,
  val inviteLinkToken: String? = null
) : DataModification<CreateUser.Result> {
  sealed interface Result : DataModification.Result {
    data class Success(val token: String) : Result
    data object InvalidPassword : Result
    data object InvalidEmail : Result
    data object InvalidInviteLink : Result
  }
}

