package dev.dwak.lender.repos.client

interface RepoRefresher<T: RepoRefresher.RefreshType> {
  sealed interface RefreshType

  suspend fun refresh(item: T)
}