package dev.dwak.lender.data.modification.group

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerProfileId

data class CreateGroupMod(
  val name: String,
  val owner: ServerProfileId,
) : DataModification<CreateGroupMod.Result> {
  sealed interface Result : DataModification.Result {
    data class Success(val groupId: ServerGroupId) : Result
  }
}
