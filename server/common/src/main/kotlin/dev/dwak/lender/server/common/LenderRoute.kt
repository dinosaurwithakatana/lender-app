package dev.dwak.lender.server.common

import dev.dwak.lender.models.server.UserIdToken
import io.ktor.http.*
import io.ktor.server.routing.*

interface LenderRoute {
  val method: HttpMethod
  val path: String
  fun handler(): RoutingHandler
}

interface AuthenticatedLenderRoute {
  val method: HttpMethod
  val path: String

  fun handler(principal: UserIdToken): RoutingHandler
}