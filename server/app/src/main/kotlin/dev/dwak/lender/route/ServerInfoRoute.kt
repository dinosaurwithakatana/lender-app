package dev.dwak.lender.route

import dev.dwak.lender.models.api.response.ApiServerInfoResponse
import dev.dwak.lender.server.common.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo

@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
@Inject
class ServerInfoRoute : LenderRoute {
  override val method: HttpMethod = HttpMethod.Get
  override val path: String = "/server-info"
  override val requestType: TypeInfo = typeInfo<Unit>()

  context(call: ApplicationCall)
  override suspend fun routeHandler() {
    call.respond(
      ApiServerInfoResponse(
        name = "lender",
        version = "1",
      )
    )
  }
}
