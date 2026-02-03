package dev.dwak.lender.datastore

import kotlinx.serialization.Serializable

@Serializable
data class DsUserInfo(
  val token: UserState,
)

sealed interface UserState {
  data class LoggedIn(val token: String, val userId: String, val email: String): UserState
  data object LoggedOut: UserState
}