package dev.dwak.models.client

import kotlin.jvm.JvmInline

sealed interface ClientUser {
  data class LoggedIn(
    val id: Id,
    val token: String,
    val email: String
  ): ClientUser {
    @JvmInline
    value class Id(
      val id: String
    )
  }

  data object LoggedOut: ClientUser
}
