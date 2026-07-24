package dev.dwak.lender.app.modification

import dev.dwak.lender.app.network.LendApi
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(AdvanceLendMod::class)
class AdvanceLendModHandler(
  private val lendApi: LendApi,
) : DataModification.Handler<AdvanceLendMod.Result, AdvanceLendMod> {
  override suspend fun handle(mod: AdvanceLendMod): AdvanceLendMod.Result {
    val response = lendApi.advanceLend(lendId = mod.lendId.id)
    return if (response.isSuccessful) AdvanceLendMod.Result.Success
    else AdvanceLendMod.Result.Error
  }
}
