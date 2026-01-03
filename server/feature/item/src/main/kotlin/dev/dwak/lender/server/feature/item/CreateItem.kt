package dev.dwak.lender.server.feature.item

import dev.dwak.lender.server.common.AuthenticatedApiRoutes
import dev.dwak.lender.server.common.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.server.routing.RoutingContext

@AuthenticatedApiRoutes
@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
class CreateItem : LenderRoute {
  override val method: HttpMethod
    get() = TODO("Not yet implemented")
  override val path: String
    get() = TODO("Not yet implemented")

  override fun handler(): suspend RoutingContext.() -> Unit = {
    TODO("Not yet implemented")
  }
}