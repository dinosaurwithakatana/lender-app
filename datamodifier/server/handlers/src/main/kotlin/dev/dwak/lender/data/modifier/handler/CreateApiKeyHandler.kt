package dev.dwak.lender.data.modifier.handler

import dev.dwak.lender.data.modification.CreateApiKey
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.db.ApiKeyQueries
import dev.dwak.lender.db.DbApiKey
import dev.dwak.lender.lender_app.generateToken
import dev.dwak.lender.models.server.ServerApiKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@ContributesIntoMap(
  scope = AppScope::class,
  binding = binding<@ModificationKey(CreateApiKey::class) BoundHandler>()
)
class CreateApiKeyHandler(
  private val apiKeyQueries: ApiKeyQueries
) : DataModification.Handler<CreateApiKey.Result, CreateApiKey> {
  @OptIn(ExperimentalUuidApi::class)
  override suspend fun handle(mod: CreateApiKey): CreateApiKey.Result {
    val apiKey: String = Uuid.generateV4().toHexString()
    apiKeyQueries.insertKey(
      DbApiKey(
        apiKey = apiKey,
        name = mod.name
      )
    )

    return CreateApiKey.Result.Success(ServerApiKey(apiKey))
  }
}