package dev.dwak.lender.app.modification

import dev.dwak.lender.data.modifier.DataModification

data object LogoutUser: DataModification<LogoutUser.Success> {
  data object Success: DataModification.Result
}
