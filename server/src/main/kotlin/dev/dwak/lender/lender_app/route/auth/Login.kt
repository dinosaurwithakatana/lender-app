package dev.dwak.lender.lender_app.route.auth

import dev.dwak.lender.lender_app.models.api.auth.ApiLoginRequest
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.login() {
    post("/login") {
        val request = call.receive<ApiLoginRequest>()
    }
}