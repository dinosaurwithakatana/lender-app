package dev.dwak.lender.data.modifier.handler

import dev.dwak.lender.data.modification.CreateInviteLink
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.db.DbInviteLink
import dev.dwak.lender.db.InviteLinkQueries
import dev.dwak.lender.lender_app.generateToken
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ClassKey
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding

@ClassKey(CreateInviteLink::class)
@ContributesIntoMap(scope = AppScope::class, binding = binding<DataModification.Handler<*, *>>())
@Inject
class CreateInviteLinkHandler(
    private val inviteLinkQueries: InviteLinkQueries,
): DataModification.Handler<CreateInviteLink.Result, CreateInviteLink> {
    override suspend fun handle(mod: CreateInviteLink): CreateInviteLink.Result {
        val linkToken = generateToken()
        inviteLinkQueries.insert(
            DbInviteLink(
                name = mod.name,
                link_token = linkToken,
                created_by_user_id = mod.createdByUserId,
                expires_at = mod.expiresOn.toString()
            )
        )

        return CreateInviteLink.Result.Success(
            inviteLink = "https://localhost:8080/invite/$linkToken"
        )
    }

}