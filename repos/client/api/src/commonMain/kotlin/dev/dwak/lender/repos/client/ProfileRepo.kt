package dev.dwak.lender.repos.client

import dev.dwak.models.client.ClientProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepo {
  val currentProfile: Flow<ClientProfile?>

  suspend fun lookupByEmail(email: String): ClientProfile?

  sealed interface RefreshTypes : RepoRefresher.RefreshType {
    data object CurrentProfile : RefreshTypes
  }
}
