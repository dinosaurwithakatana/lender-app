package dev.dwak.lender.data.modifier

data class LogoutUser(
    val id: String,
): DataModification<LogoutUser.Result> {
    sealed interface Result: DataModification.Result
}
