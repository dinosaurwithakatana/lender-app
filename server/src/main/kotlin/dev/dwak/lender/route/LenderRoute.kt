package dev.dwak.lender.route

import io.ktor.http.*
import io.ktor.server.routing.*

interface LenderRoute {
    val method: HttpMethod
    val path: String
    fun handler(): suspend RoutingContext.() -> Unit
}