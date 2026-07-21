package dev.dwak.lender.repos.client

interface RefreshableRepo<T: RefreshableRepo.RefreshItem> {
  sealed interface RefreshItem

  suspend fun refresh(item: T)
}