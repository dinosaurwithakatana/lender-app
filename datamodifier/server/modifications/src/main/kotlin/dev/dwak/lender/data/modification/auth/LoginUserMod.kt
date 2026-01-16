package dev.dwak.lender.data.modification.auth

import dev.dwak.lender.data.modifier.DataModification

data class LoginUserMod(
  val email: String,
  val password: String
) : DataModification<LoginUserMod.Result> {
  sealed interface Result : DataModification.Result {
    data class Success(val token: String) : Result
    data class Failure(val error: String) : Result
  }
}

