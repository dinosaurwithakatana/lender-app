package dev.dwak.lender.app.modification

import dev.dwak.lender.data.modifier.DataModification

data class SaveServerConfigMod(
  val serverUrl: String,
  val apiKey: String,
) : DataModification<SaveServerConfigMod.Result> {
  sealed interface Result : DataModification.Result {
    data object Success : Result
    data class Error(val message: String) : Result
  }
}
