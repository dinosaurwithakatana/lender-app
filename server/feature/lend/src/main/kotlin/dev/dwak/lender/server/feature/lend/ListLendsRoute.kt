package dev.dwak.lender.server.feature.lend

import dev.dwak.lender.models.api.request.ApiLendStatus
import dev.dwak.lender.models.api.response.ApiGetLendsResponse
import dev.dwak.lender.models.api.response.ApiLend
import dev.dwak.lender.models.api.response.ApiLendDirection
import dev.dwak.lender.models.server.ServerLend
import dev.dwak.lender.models.server.ServerLendStatus
import dev.dwak.lender.models.server.ServerProfileId
import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.repos.server.LendsRepo
import dev.dwak.lender.repos.server.ProfileRepo
import dev.dwak.lender.server.common.AuthenticatedLenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.SingleIn
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class)
class ListLendsRoute(
  private val lendsRepo: LendsRepo,
  private val profileRepo: ProfileRepo,
) : AuthenticatedLenderRoute {
  override val method: HttpMethod = HttpMethod.Get
  override val path: String = "/lend/me"

  context(call: ApplicationCall)
  override suspend fun handle(principal: UserIdToken) {
    val profileId = profileRepo.getByUserId(principal.userId)?.id
      ?: return call.respond(HttpStatusCode.NotFound, "Profile not found")

    val lends = lendsRepo.lendsForProfile(profileId).map { it.toApi(currentProfileId = profileId) }
    call.respond(ApiGetLendsResponse(lends))
  }
}

private fun ServerLend.toApi(currentProfileId: ServerProfileId): ApiLend {
  val isOutgoing = fromProfileId == currentProfileId
  return ApiLend(
    id = id.id,
    itemId = itemId.id,
    itemName = itemName,
    quantity = quantity,
    status = status.toApi(),
    direction = if (isOutgoing) ApiLendDirection.OUTGOING else ApiLendDirection.INCOMING,
    counterpartyFirstName = if (isOutgoing) toFirstName else fromFirstName,
    counterpartyLastName = if (isOutgoing) toLastName else fromLastName,
    createdAt = createdAt.toString(),
  )
}

private fun ServerLendStatus.toApi(): ApiLendStatus = when (this) {
  ServerLendStatus.REQUESTED -> ApiLendStatus.REQUESTED
  ServerLendStatus.APPROVED -> ApiLendStatus.APPROVED
  ServerLendStatus.DENIED -> ApiLendStatus.DENIED
  ServerLendStatus.LENT -> ApiLendStatus.LENT
  ServerLendStatus.RETURNED -> ApiLendStatus.RETURNED
}
