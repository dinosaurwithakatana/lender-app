package dev.dwak.lender.datastore.dev.dwak.lender.datastore

import androidx.datastore.core.okio.OkioSerializer
import dev.dwak.lender.datastore.DsServerConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.okio.decodeFromBufferedSource
import kotlinx.serialization.json.okio.encodeToBufferedSink
import okio.BufferedSink
import okio.BufferedSource

object ServerConfigSerializer : OkioSerializer<DsServerConfig> {
  override val defaultValue: DsServerConfig = DsServerConfig()

  @OptIn(ExperimentalSerializationApi::class)
  override suspend fun readFrom(source: BufferedSource): DsServerConfig {
    return Json.decodeFromBufferedSource<DsServerConfig>(source)
  }

  @OptIn(ExperimentalSerializationApi::class)
  override suspend fun writeTo(t: DsServerConfig, sink: BufferedSink) {
    Json.encodeToBufferedSink(t, sink)
  }
}
