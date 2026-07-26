package dev.dwak.lender.app.modification

import androidx.datastore.core.DataStore
import dev.dwak.lender.data.modifier.DataModification
import dev.dwak.lender.data.modifier.handler.ModificationKey
import dev.dwak.lender.datastore.DsServerConfig
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap

@ContributesIntoMap(scope = AppScope::class)
@ModificationKey(SaveServerConfigMod::class)
class SaveServerConfigHandler(
  private val dataStore: DataStore<DsServerConfig>,
) : DataModification.Handler<SaveServerConfigMod.Result, SaveServerConfigMod> {
  override suspend fun handle(mod: SaveServerConfigMod): SaveServerConfigMod.Result {
    return try {
      dataStore.updateData {
        DsServerConfig(serverUrl = mod.serverUrl, apiKey = mod.apiKey)
      }
      SaveServerConfigMod.Result.Success
    } catch (e: Throwable) {
      SaveServerConfigMod.Result.Error(e.message ?: "Failed to save server config")
    }
  }
}
