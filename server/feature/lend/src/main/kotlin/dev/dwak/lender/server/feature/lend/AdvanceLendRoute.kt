package dev.dwak.lender.server.feature.lend

import dev.dwak.lender.data.modification.lend.AdvanceLendMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.server.ServerLendId
import dev.dwak.lender.models.server.UserIdToken
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
class AdvanceLendRoute(
  private val dataModifier: DataModifier,
  private val profileRepo: ProfileRepo,
) : AuthenticatedLenderRoute {
  override val method: HttpMethod = HttpMethod.Post
  override val path: String = "/lend/{lendId}/advance"

  context(call: ApplicationCall)
  override suspend fun handle(principal: UserIdToken) {
    val lendId = call.parameters["lendId"]
      ?: return call.respond(HttpStatusCode.BadRequest, "Missing lendId")

    val profileId = profileRepo.getByUserId(principal.userId)?.id
      ?: return call.respond(HttpStatusCode.NotFound, "Profile not found")

    when (dataModifier.submit(
      AdvanceLendMod(
        lendId = ServerLendId(lendId),
        actorProfileId = profileId,
      )
    )) {
      AdvanceLendMod.Result.NotFound -> call.respond(HttpStatusCode.NotFound)
      AdvanceLendMod.Result.Unauthorized -> call.respond(HttpStatusCode.Forbidden)
      AdvanceLendMod.Result.InvalidTransition -> call.respond(HttpStatusCode.Conflict)
      AdvanceLendMod.Result.Approved,
      AdvanceLendMod.Result.MarkedLent -> call.respond(HttpStatusCode.OK)
    }
  }
}
