package dev.dwak.lender.data.modifier.handler.auth

import dev.dwak.lender.data.modification.auth.CreateInviteLinkMod
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.BoundHandler
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.db.DbInviteLink
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.InviteLinkQueries
import dev.dwak.lender.lender_app.generateToken
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding

@ContributesIntoMap(
  scope = AppScope::class,
  binding = binding<@ModificationKey(CreateInviteLinkMod::class) BoundHandler>()
)
class CreateInviteLinkHandler(
  private val inviteLinkQueries: InviteLinkQueries,
) : DataModification.Handler<CreateInviteLinkMod.Result, CreateInviteLinkMod> {
  override suspend fun handle(mod: CreateInviteLinkMod): CreateInviteLinkMod.Result {
    val linkToken = generateToken()
    inviteLinkQueries.insert(
      DbInviteLink(
        name = mod.name,
        link_token = DbInviteLink.Link_token(linkToken),
        created_by_profile_id = DbProfile.Id(mod.createdByProfileId.id),
        expires_at = mod.expiresOn
      )
    )

    return CreateInviteLinkMod.Result.Success(
      inviteLink = "https://localhost:8080/invite/$linkToken"
    )
  }

}