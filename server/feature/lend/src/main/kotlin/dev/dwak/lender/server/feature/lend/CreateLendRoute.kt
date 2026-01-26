package dev.dwak.lender.server.feature.lend

import dev.dwak.lender.data.modification.lend.CreateLendMod
import dev.dwak.lender.data.modifier.DataModifier
import dev.dwak.lender.models.api.request.ApiCreateLend
import dev.dwak.lender.models.api.request.ApiLendStatus
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerItemId
import dev.dwak.lender.models.server.ServerLendStatus
import dev.dwak.lender.models.server.ServerProfileId
import dev.dwak.lender.models.server.UserIdToken
import dev.dwak.lender.repos.server.GroupMembershipRepo
import dev.dwak.lender.repos.server.GroupsRepo
import dev.dwak.lender.repos.server.ItemRepo
import dev.dwak.lender.repos.server.ProfileRepo
import dev.dwak.lender.server.common.AuthenticatedTypedLenderRoute
import dev.dwak.lender.server.common.LenderRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo

@SingleIn(AppScope::class)
@ContributesIntoSet(AppScope::class, binding = binding<LenderRoute>())
class CreateLendRoute(
  private val dataModifier: DataModifier,
  private val groupsRepo: GroupsRepo,
  private val itemRepo: ItemRepo,
  private val profileRepo: ProfileRepo,
  private val groupMembershipRepo: GroupMembershipRepo,
) : AuthenticatedTypedLenderRoute<ApiCreateLend> {
  override val requestType: TypeInfo = typeInfo<ApiCreateLend>()
  override val method: HttpMethod = HttpMethod.Post
  override val path: String = "/lend"


  context(call: ApplicationCall)
  override suspend fun handle(request: ApiCreateLend, principal: UserIdToken) {
    val sourceProfile = profileRepo.getByUserId(principal.userId)
    val targetProfile = profileRepo.getProfileById(ServerProfileId(request.toProfileId))
    val item = itemRepo.getItemById(ServerItemId(request.itemId))
    val group = groupsRepo.groupById(ServerGroupId(request.groupId))
    val itemGroups = groupsRepo.groupsForItem(ServerItemId(request.itemId))

    if (item == null) {
      call.respond(HttpStatusCode.NotFound)
    }

    if (sourceProfile == null) {
      call.respond(HttpStatusCode.NotFound)
    }

    if (targetProfile == null) {
      call.respond(HttpStatusCode.NotFound)
    }

    if (item?.ownedBy != sourceProfile?.id) {
      call.respond(HttpStatusCode.Unauthorized)
    }

    if (!itemGroups.contains(group)) {
      call.respond(HttpStatusCode.Unauthorized)
    }

    val sourceProfileInGroup = groupMembershipRepo.isProfileInGroup(
      profile = sourceProfile!!.id,
      group = group!!.id
    )

    val targetProfileInGroup = groupMembershipRepo.isProfileInGroup(
      profile = targetProfile!!.id,
      group = group.id
    )

    if (!sourceProfileInGroup || !targetProfileInGroup) {
      call.respond(HttpStatusCode.Unauthorized)
    }


    when (val result = dataModifier.submit(
      CreateLendMod(
        itemId = item!!.id,
        groupId = group.id,
        fromProfileId = sourceProfile.id,
        toProfileId = targetProfile.id,
        status = when (request.lendStatus) {
          ApiLendStatus.REQUESTED -> ServerLendStatus.REQUESTED
          ApiLendStatus.APPROVED -> ServerLendStatus.APPROVED
          ApiLendStatus.DENIED -> ServerLendStatus.DENIED
          ApiLendStatus.LENT -> ServerLendStatus.LENT
          ApiLendStatus.RETURNED -> ServerLendStatus.RETURNED
        },
        quantity = request.quantity
      )
    )) {
      CreateLendMod.Result.Success -> {
        call.respond(HttpStatusCode.OK)
      }
    }
  }
}
