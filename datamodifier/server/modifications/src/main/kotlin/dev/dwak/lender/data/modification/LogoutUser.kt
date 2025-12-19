package dev.dwak.lender.data.modification

import dev.dwak.lender.data.modifier.DataModification

data class LogoutUser(
    val token: String,
) : DataModification<LogoutUser.Result> {
    sealed interface Result : DataModification.Result {
        data object Success : Result
    }
}

