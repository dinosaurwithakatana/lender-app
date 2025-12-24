package dev.dwak.lender.repos.server

import dev.dwak.lender.db.ApiKeyQueries
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class RealApiKeyRepo(
  private val queries: ApiKeyQueries,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ApiKeyRepo {
  override suspend fun hasKey(key: String): Boolean = withContext(dispatcher) {
    queries.hasKey(key).executeAsOne()
  }
}