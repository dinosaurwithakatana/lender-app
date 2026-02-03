package dev.dwak.lender.datastore.dev.dwak.lender.datastore

import androidx.datastore.core.okio.OkioSerializer
import dev.dwak.lender.datastore.UserState
import dev.dwak.lender.datastore.DsUserInfo
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.okio.decodeFromBufferedSource
import kotlinx.serialization.json.okio.encodeToBufferedSink
import okio.BufferedSink
import okio.BufferedSource

object UserInfoSerializer : OkioSerializer<DsUserInfo> {
  override val defaultValue: DsUserInfo = DsUserInfo(UserState.LoggedOut)

  @OptIn(ExperimentalSerializationApi::class)
  override suspend fun readFrom(source: BufferedSource): DsUserInfo {
    return Json.Default.decodeFromBufferedSource<DsUserInfo>(source)
  }

  @OptIn(ExperimentalSerializationApi::class)
  override suspend fun writeTo(t: DsUserInfo, sink: BufferedSink) {
    Json.Default.encodeToBufferedSink(t, sink)
  }
}