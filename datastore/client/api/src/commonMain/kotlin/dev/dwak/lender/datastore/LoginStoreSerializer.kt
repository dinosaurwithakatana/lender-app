package dev.dwak.lender.datastore

import androidx.datastore.core.okio.OkioSerializer
import okio.BufferedSink
import okio.BufferedSource


object LoginStoreSerializer: OkioSerializer<LoginStore> {
  override val defaultValue: LoginStore
    get() = TODO("Not yet implemented")

  override suspend fun readFrom(source: BufferedSource): LoginStore {
    TODO("Not yet implemented")
  }

  override suspend fun writeTo(t: LoginStore, sink: BufferedSink) {
    TODO("Not yet implemented")
  }
}