package dev.dwak.lender.repos.server

import dev.dwak.lender.db.DbGroup
import dev.dwak.lender.db.DbUser
import dev.dwak.lender.db.GroupMembershipQueries
import dev.dwak.lender.db.ProfileQueries
import dev.dwak.lender.lender_app.coroutines.Io
import dev.dwak.lender.models.server.ServerGroupId
import dev.dwak.lender.models.server.ServerProfile
import dev.dwak.lender.models.server.ServerProfileId
import dev.dwak.lender.models.server.ServerUserId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@ContributesBinding(AppScope::class)
class RealProfileRepo(
  private val profileQueries: ProfileQueries,
  private val groupMembershipQueries: GroupMembershipQueries,
  @Io private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProfileRepo {
  override suspend fun getByEmail(email: String): ServerProfile = withContext(dispatcher) {
    profileQueries.findByEmail(email) { id, _, first_name, last_name ->
      ServerProfile(
        id = ServerProfileId(id.id),
        firstName = first_name,
        lastName = last_name,
      )
    }.executeAsOne()
  }

  override suspend fun listProfiles(): List<ServerProfile> {
    return profileQueries.selectAll { id, _, first_name, last_name ->
      ServerProfile(
        id = ServerProfileId(id.id),
        firstName = first_name,
        lastName = last_name
      )
    }.executeAsList()
  }

  override suspend fun getByUserId(userId: ServerUserId): ServerProfile {
    return profileQueries.findByUserId(DbUser.Id(userId.id))
    { id, _, first_name, last_name ->
      ServerProfile(
        id = ServerProfileId(id.id),
        firstName = first_name,
        lastName = last_name
      )
    }
      .executeAsOne()
  }

  override suspend fun getProfilesInGroup(id: ServerGroupId): List<ServerProfile> =
    withContext(dispatcher) {
      groupMembershipQueries.profilesInGroup(DbGroup.Id(id.id)) { id, user_id, first_name, last_name ->
        ServerProfile(
          id = ServerProfileId(id.id),
          firstName = first_name,
          lastName = last_name
        )

      }
        .executeAsList()
    }
}