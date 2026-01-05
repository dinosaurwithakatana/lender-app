package dev.dwak.lender.data.modification.item

import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerItemId
import dev.dwak.lender.models.server.ServerProfileId

data class ShareItem(
  val itemId: ServerItemId,
  val groupId: ServerGroupId,
  val profileId: ServerProfileId
): DataModification<ShareItem.Result> {
  sealed interface Result: DataModification.Result {
    data object Success: Result
    data object Unauthorized: Result
    data object ItemNotFound: Result
    data object GroupNotFound: Result
    data object UnknownError: Result
  }
}
