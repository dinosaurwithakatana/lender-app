package dev.dwak.lender.data.modification.auth

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.models.server.ServerApiKey

data class CreateApiKeyMod(
  val name: String,
) : DataModification<CreateApiKeyMod.Result> {
  sealed interface Result : DataModification.Result {
    data class Success(val apiKey: ServerApiKey) : Result
  }
}
