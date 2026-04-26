package dev.dwak.lender.data.modification.profile

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.models.server.ServerProfile

data class CreateGuestProfileMod(
  val firstName: String,
  val lastName: String,
) : DataModification<CreateGuestProfileMod.Result> {
  sealed interface Result : DataModification.Result {
    data class Success(val profile: ServerProfile) : Result
  }
}
