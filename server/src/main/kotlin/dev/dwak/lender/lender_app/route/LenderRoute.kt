package dev.dwak.lender.lender_app.route

import io.ktor.http.*
import io.ktor.server.routing.*

interface LenderRoute {
    val method: HttpMethod
    val path: String
    val parent: LenderRoute?
    fun handler(): suspend RoutingContext.() -> Unit
}