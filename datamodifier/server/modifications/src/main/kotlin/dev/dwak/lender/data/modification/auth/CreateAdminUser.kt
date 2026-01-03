package dev.dwak.lender.data.modification.auth

import dev.dwak.lender.data.modifier.DataModification

data class CreateAdminUser(
  val email: String,
  val password: String,
  val firstName: String,
  val lastName: String,
) : DataModification<CreateAdminUser.Result> {
  sealed interface Result : DataModification.Result {
    data class Success(val token: String) : Result
    data object InvalidPassword : Result
    data object InvalidEmail : Result
  }
}

