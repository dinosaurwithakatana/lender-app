package dev.dwak.lender.repos.client

import dev.dwak.lender.app.network.LendApi
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.lender.models.api.request.ApiLendStatus
import dev.dwak.lender.models.api.response.ApiLend
import dev.dwak.lender.models.api.response.ApiLendDirection
import dev.dwak.models.client.ClientLend
import dev.dwak.models.client.ClientLendStatus
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

@ContributesBinding(scope = AppScope::class, binding = binding<LendsRepo>())
@ContributesBinding(
  scope = AppScope::class,
  binding = binding<RepoRefresher<LendsRepo.RefreshTypes>>()
)
@SingleIn(AppScope::class)
class RealLendsRepo(
  private val lendApi: LendApi,
  @Io private val dispatcher: CoroutineDispatcher,
) : LendsRepo, RepoRefresher<LendsRepo.RefreshTypes> {
  override val currentUserLends: Flow<List<ClientLend>>
    field = MutableStateFlow(emptyList())

  override suspend fun refresh(item: LendsRepo.RefreshTypes): Unit = withContext(dispatcher) {
    when (item) {
      LendsRepo.RefreshTypes.CurrentUserLends -> {
        val response = lendApi.getActiveLendsForCurrentUser()
        currentUserLends.value = if (response.isSuccessful) {
          response.body()?.lends?.map { it.toClient() } ?: emptyList()
        } else {
          emptyList()
        }
      }
    }
  }
}

private fun ApiLend.toClient(): ClientLend = ClientLend(
  id = ClientLend.Id(id),
  itemName = itemName,
  quantity = quantity,
  status = status.toClient(),
  direction = when (direction) {
    ApiLendDirection.OUTGOING -> ClientLend.Direction.OUTGOING
    ApiLendDirection.INCOMING -> ClientLend.Direction.INCOMING
  },
  counterpartyFirstName = counterpartyFirstName,
  counterpartyLastName = counterpartyLastName,
  createdAt = createdAt,
)

private fun ApiLendStatus.toClient(): ClientLendStatus = when (this) {
  ApiLendStatus.REQUESTED -> ClientLendStatus.REQUESTED
  ApiLendStatus.APPROVED -> ClientLendStatus.APPROVED
  ApiLendStatus.DENIED -> ClientLendStatus.DENIED
  ApiLendStatus.LENT -> ClientLendStatus.LENT
  ApiLendStatus.RETURNED -> ClientLendStatus.RETURNED
}
