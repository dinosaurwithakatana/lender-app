package dev.dwak.lender.data.modification.auth

import dev.dwak.lender.data.modifier.DataModification

data class CreateAdminUserMod(
  val email: String,
  val password: String,
  val firstName: String,
  val lastName: String,
) : DataModification<CreateAdminUserMod.Result> {
  sealed interface Result : DataModification.Result {
    data class Success(val token: String) : Result
    data object InvalidPassword : Result
    data object InvalidEmail : Result
  }
}

