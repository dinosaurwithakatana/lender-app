package dev.dwak.lender.app.modification

import dev.dwak.lender.app.network.LendApi
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.models.api.request.ApiCreateLend
import dev.dwak.lender.models.api.request.ApiLendStatus
import dev.dwak.lender.models.api.response.ApiLend
import dev.dwak.models.client.ClientLendStatus
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(CreateGuestLendMod::class)
class CreateGuestLendModHandler(
  private val lendApi: LendApi,
) : DataModification.Handler<CreateGuestLendMod.Result, CreateGuestLendMod> {
  override suspend fun handle(mod: CreateGuestLendMod): CreateGuestLendMod.Result {
    val response = lendApi.createLend(
      payload = ApiCreateLend.ToGuest(
        firstName = mod.firstName,
        lastName = mod.lastName,
        itemId = mod.itemId.id,
        lendStatus = when (mod.lendStatus) {
          ClientLendStatus.REQUESTED -> ApiLendStatus.REQUESTED
          ClientLendStatus.APPROVED -> ApiLendStatus.APPROVED
          ClientLendStatus.DENIED -> ApiLendStatus.DENIED
          ClientLendStatus.LENT -> ApiLendStatus.LENT
          ClientLendStatus.RETURNED -> ApiLendStatus.RETURNED
        },
        quantity = mod.quantity,
      )
    )
    return if (response.isSuccessful) CreateGuestLendMod.Result.Success
    else CreateGuestLendMod.Result.Error
  }
}
