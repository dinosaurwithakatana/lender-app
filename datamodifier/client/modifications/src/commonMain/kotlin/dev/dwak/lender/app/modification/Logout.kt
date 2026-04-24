package dev.dwak.lender.app.modification

import dev.dwak.lender.data.modifier.DataModification

data object Logout : DataModification<Logout.Result> {
  sealed interface Result : DataModification.Result {
    data object Success : Result
    data object Error : Result
  }
}
