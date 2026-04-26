package dev.dwak.lender.data.modification.profile

import dev.dwak.lender.data.modifier.DataModification

data class CreateGuestProfileMod(
  val firstName: String,
  val lastName: String,
): DataModification<CreateGuestProfileMod.Result> {
  sealed interface Result: DataModification.Result {

  }
}
