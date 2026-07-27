package dev.dwak.lender.app.modification

import dev.dwak.lender.data.modifier.DataModification

data class SignUpUserMod(
  val email: String,
  val password: String,
  val confirmPassword: String,
  val firstName: String,
  val lastName: String,
  val inviteLinkToken: String,
): DataModification<SignUpUserMod.Result> {
  sealed interface Result : DataModification.Result {
    data object Success: Result
    data object Error: Result
  }
}
