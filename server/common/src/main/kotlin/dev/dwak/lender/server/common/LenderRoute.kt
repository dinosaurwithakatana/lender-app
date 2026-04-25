@file:OptIn(ExperimentalMetroApi::class)

package dev.dwak.lender.server.common

import dev.dwak.lender.models.server.UserIdToken
import dev.zacsweers.metro.DefaultBinding
import dev.zacsweers.metro.ExperimentalMetroApi
import io.ktor.http.*
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo

@DefaultBinding<LenderRoute>
interface LenderRoute {
  val method: HttpMethod
  val path: String
  val requestType: TypeInfo

  context(call: ApplicationCall)
  suspend fun routeHandler()
}

@DefaultBinding<LenderRoute>
interface TypedLenderRoute<T : Any> : LenderRoute {
  override val requestType: TypeInfo

  context(call: ApplicationCall)
  suspend fun handle(request: T)

  context(call: ApplicationCall)
  override suspend fun routeHandler() {
    val request = call.receive<T>(requestType)
    handle(request)
  }
}

@DefaultBinding<LenderRoute>
interface AuthenticatedLenderRoute : LenderRoute {
  override val requestType: TypeInfo
    get() = typeInfo<Unit>()

  context(call: ApplicationCall)
  suspend fun handle(principal: UserIdToken)

  context(call: ApplicationCall)
  override suspend fun routeHandler() {
    handle(call.principal<UserIdToken>()!!)
  }
}

@DefaultBinding<LenderRoute>
interface AuthenticatedTypedLenderRoute<T : Any> : TypedLenderRoute<T> {
  context(call: ApplicationCall)
  suspend fun handle(request: T, principal: UserIdToken)

  context(call: ApplicationCall)
  override suspend fun handle(request: T) {
    handle(request, call.principal<UserIdToken>()!!)
  }
}