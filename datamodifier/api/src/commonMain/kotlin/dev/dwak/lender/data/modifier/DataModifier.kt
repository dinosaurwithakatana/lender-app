package dev.dwak.lender.data.modifier

import dev.zacsweers.metro.DefaultBinding
import dev.zacsweers.metro.ExperimentalMetroApi

interface DataModifier {
  suspend fun <R : DataModification.Result> submit(mod: DataModification<R>): R
}

interface DataModification<R : DataModification.Result> {
  interface Result

  @OptIn(ExperimentalMetroApi::class)
  @DefaultBinding<DataModification.Handler<*, *>>
  interface Handler<R : Result, M : DataModification<R>> {
    suspend fun handle(mod: M): R
  }
}
