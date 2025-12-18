package dev.dwak.lender.data.modifier

import dev.dwak.lender.db.TokenQueries
import dev.zacsweers.metro.*

data class LogoutUser(
    val token: String,
) : DataModification<LogoutUser.Result> {
    sealed interface Result : DataModification.Result {
        data object Success : Result
    }
}

@ClassKey(LogoutUser::class)
@ContributesIntoMap(scope = AppScope::class, binding = binding<DataModification.Handler<*, *>>())
@Inject
class LogoutUserHandler(
    private val tokenQueries: TokenQueries
) : DataModification.Handler<LogoutUser.Result, LogoutUser> {
    override suspend fun handle(mod: LogoutUser): LogoutUser.Result {
        println(tokenQueries.deleteToken(mod.token).await())
        return LogoutUser.Result.Success
    }
}
