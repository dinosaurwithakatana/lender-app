package dev.dwak.lender.datastore.dev.dwak.lender.datastore

import androidx.datastore.core.Storage
import androidx.datastore.core.okio.WebStorage
import androidx.datastore.core.okio.WebStorageType
import dev.dwak.lender.datastore.DsUserInfo
import kotlinx.io.files.Path

actual fun createUserInfoStorage(appDir: Path): Storage<DsUserInfo> = WebStorage(
  serializer = UserInfoSerializer,
  name = "user_info.json",
  storageType = WebStorageType.SESSION
)