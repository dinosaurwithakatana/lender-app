package dev.dwak.lender.data.modifier

interface DataModifier {
    suspend fun <R : DataModification.Result> submit(mod: DataModification<R>): R
}

interface DataModification<R : DataModification.Result> {
    interface Result

    interface Handler<R : Result, M : DataModification<R>> {
        suspend fun handle(mod: M): R
    }
}
