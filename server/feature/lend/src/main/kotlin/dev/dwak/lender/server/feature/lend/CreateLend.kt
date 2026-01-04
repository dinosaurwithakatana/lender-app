package dev.dwak.lender.server.feature.lend

import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.server.common.AuthenticatedLenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.server.routing.RoutingHandler

@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
class CreateLend : AuthenticatedLenderRoute {
  override val method: HttpMethod
    get() = TODO("Not yet implemented")
  override val path: String
    get() = TODO("Not yet implemented")

  override fun handler(principal: UserIdToken): RoutingHandler = {
    TODO("Not yet implemented")
  }
}