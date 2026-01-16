package dev.dwak.lender.data.modification.auth

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.models.server.ServerToken

data class LogoutUserMod(
  val token: ServerToken,
) : DataModification<LogoutUserMod.Result> {
  sealed interface Result : DataModification.Result {
    data object Success : Result
  }
}

