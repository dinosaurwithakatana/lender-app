package dev.dwak.lender.datastore

import kotlinx.serialization.Serializable

@Serializable
data class DsUserInfo(
  val userState: UserState,
)

@Serializable
sealed interface UserState {
  @Serializable
  data class LoggedIn(val token: String, val userId: String, val email: String): UserState

  @Serializable
  data object LoggedOut: UserState
}