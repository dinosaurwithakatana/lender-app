package dev.dwak.lender.repos.server

import dev.dwak.lender.db.ProfileQueries
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.lender.models.server.ServerProfile
import dev.dwak.lender.models.server.ServerProfileId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@ContributesBinding(AppScope::class)
class RealProfileRepo(
    private val profileQueries: ProfileQueries,
    @Io private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProfileRepo {
    override suspend fun getByEmail(email: String): ServerProfile = withContext(dispatcher) {
        profileQueries.findByEmail(email) { id, user_id, first_name, last_name ->
            ServerProfile(
                id = ServerProfileId(id.id),
                firstName = first_name,
                lastName = last_name,
            )
        }.executeAsOne()
    }

    override suspend fun listProfiles(): List<ServerProfile> {
        return profileQueries.selectAll { id, user_id, first_name, last_name ->
            ServerProfile(
                id = ServerProfileId(id.id),
                firstName = first_name,
                lastName = last_name
            )
        }.executeAsList()
    }
}