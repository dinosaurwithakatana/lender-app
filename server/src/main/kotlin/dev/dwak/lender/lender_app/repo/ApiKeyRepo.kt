package dev.dwak.lender.lender_app.repo

import dev.dwak.lender.db.ApiKeyQueries
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@SingleIn(AppScope::class)
@Inject
class ApiKeyRepo(
    private val queries: ApiKeyQueries,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun hasKey(key: String): Boolean = withContext(dispatcher) {
        queries.hasKey(key).executeAsOne()
    }
}