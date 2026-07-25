package dev.dwak.lender.repos.client

import dev.dwak.lender.app.network.ProfileApi
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.models.client.ClientProfile
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

@ContributesBinding(scope = AppScope::class, binding = binding<ProfileRepo>())
@ContributesBinding(
  scope = AppScope::class,
  binding = binding<RepoRefresher<ProfileRepo.RefreshTypes>>()
)
@SingleIn(AppScope::class)
class RealProfileRepo(
  private val profileApi: ProfileApi,
  @Io private val dispatcher: CoroutineDispatcher,
) : ProfileRepo, RepoRefresher<ProfileRepo.RefreshTypes> {
  override val currentProfile: Flow<ClientProfile?>
    field = MutableStateFlow<ClientProfile?>(null)

  override suspend fun refresh(item: ProfileRepo.RefreshTypes) = withContext(dispatcher) {
    when (item) {
      ProfileRepo.RefreshTypes.CurrentProfile -> {
        val response = profileApi.getCurrentProfile()
        currentProfile.value = if (response.isSuccessful) {
          response.body()?.let {
            ClientProfile(
              id = ClientProfile.Id(it.profileId),
              firstName = it.firstName,
              lastName = it.lastName,
            )
          }
        } else {
          null
        }
      }
    }
  }
}
