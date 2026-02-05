package dev.dwak.lender.repos.client

import dev.dwak.models.client.ClientUser
import kotlinx.coroutines.flow.StateFlow

interface UserRepo {
  fun currentUser(): StateFlow<ClientUser>
}