package dev.dwak.lender.app.modification

import dev.dwak.lender.data.modifier.DataModification
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding

data class LoginUser(
  val email: String,
  val password: String
): DataModification<LoginUser.Result> {
  sealed interface Result: DataModification.Result {
    data object Success: Result
  }
}