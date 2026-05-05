package dev.dwak.lender.app.modification

import dev.dwak.lender.data.modifier.DataModification

data object LogoutMod : DataModification<LogoutMod.Result> {
  sealed interface Result : DataModification.Result {
    data object Success : Result
    data object Error : Result
  }
}
