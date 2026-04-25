package dev.dwak.lender.route

import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.repos.server.UserRepo
import dev.dwak.lender.server.common.AuthenticatedLenderRoute
import dev.dwak.lender.server.common.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import io.ktor.http.HttpMethod
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respondText
import io.ktor.server.routing.RoutingHandler
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo

@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
@Inject
class BearerTest(
  private val userRepo: UserRepo,
) : AuthenticatedLenderRoute {
  override val method: HttpMethod
    get() = HttpMethod.Get
  override val path: String
    get() = "/test-auth"

  context(call: ApplicationCall)
  override suspend fun handle(principal: UserIdToken) {
    val userId = principal.userId
    val user = userRepo.getUserById(userId)
    call.respondText("Hello ${user.email}!")
  }
}