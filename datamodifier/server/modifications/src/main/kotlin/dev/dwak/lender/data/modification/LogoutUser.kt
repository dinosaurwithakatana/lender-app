package dev.dwak.lender.data.modification

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.models.server.ServerToken

data class LogoutUser(
  val token: ServerToken,
) : DataModification<LogoutUser.Result> {
  sealed interface Result : DataModification.Result {
    data object Success : Result
  }
}

