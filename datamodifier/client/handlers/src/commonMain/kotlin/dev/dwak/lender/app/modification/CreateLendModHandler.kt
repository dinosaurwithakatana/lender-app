package dev.dwak.lender.app.modification

import dev.dwak.lender.app.network.LendApi
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.models.api.request.ApiCreateLend
import dev.dwak.lender.models.api.request.ApiLendStatus
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(CreateLendMod::class)
class CreateLendModHandler(
  private val lendApi: LendApi,
) : DataModification.Handler<CreateLendMod.Result, CreateLendMod> {
  override suspend fun handle(mod: CreateLendMod): CreateLendMod.Result {
    val response = lendApi.createLend(
      payload = ApiCreateLend.ToProfile(
        toProfileId = mod.toProfileId.id,
        itemId = mod.itemId.id,
        groupId = mod.groupId.id,
        lendStatus = ApiLendStatus.REQUESTED,
        quantity = mod.quantity,
      )
    )
    return if (response.isSuccessful) CreateLendMod.Result.Success
    else CreateLendMod.Result.Error
  }
}
