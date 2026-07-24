package dev.dwak.lender.repos.server

import dev.dwak.lender.models.server.ServerLend
import dev.dwak.lender.models.server.ServerProfileId

interface LendsRepo {
  suspend fun activeLendsForProfile(id: ServerProfileId): List<ServerLend>
}
