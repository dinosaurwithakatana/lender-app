package dev.dwak.lender.data.modifier

import dev.dwak.lender.lender_app.coroutines.Io
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.SingleIn
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlin.reflect.KClass

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
@Inject
class RealDataModifier(
    private val handlerMap: Map<KClass<*>, Provider<DataModification.Handler<*, *>>>,
    @Io private val dispatcher: CoroutineDispatcher,
) : DataModifier {
    override suspend fun <R : DataModification.Result> submit(mod: DataModification<R>): R = withContext(dispatcher) {
        Napier.d { "Received: $mod" }
        @Suppress("UNCHECKED_CAST")
        (handlerMap[mod::class] as Provider<DataModification.Handler<R, DataModification<R>>>)()
            .handle(mod)
    }
}