package dev.dwak.lender.server.common

import io.ktor.http.*
import io.ktor.server.routing.*

interface LenderRoute {
  val method: HttpMethod
  val path: String
  fun handler(): RoutingHandler
}