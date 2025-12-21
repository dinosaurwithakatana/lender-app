package dev.dwak.lender.data.modifier.handler

import dev.dwak.lender.data.modification.CreateInviteLink
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.db.DbInviteLink
import dev.dwak.lender.db.DbProfile
import dev.dwak.lender.db.DbToken
import dev.dwak.lender.db.InviteLinkQueries
import dev.dwak.lender.lender_app.generateToken
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding

@ContributesIntoMap(
    scope = AppScope::class,
    binding = binding<@ModificationKey(CreateInviteLink::class) BoundHandler>()
)
class CreateInviteLinkHandler(
    private val inviteLinkQueries: InviteLinkQueries,
): DataModification.Handler<CreateInviteLink.Result, CreateInviteLink> {
    override suspend fun handle(mod: CreateInviteLink): CreateInviteLink.Result {
        val linkToken = generateToken()
        inviteLinkQueries.insert(
            DbInviteLink(
                name = mod.name,
                link_token = DbInviteLink.Link_token(linkToken),
                created_by_profile_id = DbProfile.Id(mod.createdByProfileId.id),
                expires_at = mod.expiresOn.toString()
            )
        )

        return CreateInviteLink.Result.Success(
            inviteLink = "https://localhost:8080/invite/$linkToken"
        )
    }

}