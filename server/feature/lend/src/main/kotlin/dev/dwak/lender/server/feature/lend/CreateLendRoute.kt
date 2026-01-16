package dev.dwak.lender.server.feature.lend

import dev.dwak.lender.models.api.request.ApiCreateLend
import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.server.common.AuthenticatedTypedLenderRoute
import dev.dwak.lender.server.common.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import io.ktor.http.HttpMethod
import io.ktor.server.application.ApplicationCall
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo

@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class, binding = binding<LenderRoute>())
class CreateLendRoute : AuthenticatedTypedLenderRoute<ApiCreateLend> {
  override val requestType: TypeInfo = typeInfo<ApiCreateLend>()
  override val method: HttpMethod = HttpMethod.Post
  override val path: String = "/lend"


  context(call: ApplicationCall)
  override suspend fun handle(request: ApiCreateLend, principal: UserIdToken) {
    TODO("Not yet implemented")
  }

}
