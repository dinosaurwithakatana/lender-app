package dev.dwak.lender.app.modification

import dev.dwak.lender.app.network.LendApi
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(DeleteLendMod::class)
class DeleteLendModHandler(
  private val lendApi: LendApi,
) : DataModification.Handler<DeleteLendMod.Result, DeleteLendMod> {
  override suspend fun handle(mod: DeleteLendMod): DeleteLendMod.Result {
    val response = lendApi.deleteLend(lendId = mod.lendId.id)
    return if (response.isSuccessful) DeleteLendMod.Result.Success
    else DeleteLendMod.Result.Error
  }
}
