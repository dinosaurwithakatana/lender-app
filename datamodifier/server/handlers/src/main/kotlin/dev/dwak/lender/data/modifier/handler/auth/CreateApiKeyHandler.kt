package dev.dwak.lender.data.modifier.handler.auth

import dev.dwak.lender.data.modification.auth.CreateApiKeyMod
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.BoundHandler
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.db.ApiKeyQueries
import dev.dwak.lender.db.DbApiKey
import dev.dwak.lender.models.server.ServerApiKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@ContributesIntoMap(
  scope = AppScope::class,
  binding = binding<@ModificationKey(CreateApiKeyMod::class) BoundHandler>()
)
class CreateApiKeyHandler(
  private val apiKeyQueries: ApiKeyQueries
) : DataModification.Handler<CreateApiKeyMod.Result, CreateApiKeyMod> {
  @OptIn(ExperimentalUuidApi::class)
  override suspend fun handle(mod: CreateApiKeyMod): CreateApiKeyMod.Result {
    val apiKey: String = Uuid.generateV4().toHexString()
    apiKeyQueries.insertKey(
      DbApiKey(
        apiKey = apiKey,
        name = mod.name
      )
    )

    return CreateApiKeyMod.Result.Success(ServerApiKey(apiKey))
  }
}