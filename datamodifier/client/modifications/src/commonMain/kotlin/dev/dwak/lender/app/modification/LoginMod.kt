package dev.dwak.lender.app.modification

import dev.dwak.lender.data.modifier.DataModification

data class LoginUserMod(
  val email: String,
  val password: String
): DataModification<LoginUserMod.Result> {
  sealed interface Result: DataModification.Result {
    data object Success: Result
    data object Error: Result
  }
}