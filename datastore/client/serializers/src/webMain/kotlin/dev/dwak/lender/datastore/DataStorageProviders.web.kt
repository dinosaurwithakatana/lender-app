package dev.dwak.lender.datastore.dev.dwak.lender.datastore

import androidx.datastore.core.Storage
import androidx.datastore.core.okio.WebLocalStorage
import dev.dwak.lender.datastore.DsUserInfo
import kotlinx.io.files.Path

actual fun createUserInfoStorage(appDir: Path): Storage<DsUserInfo> = WebLocalStorage(
  serializer = UserInfoSerializer,
  name = "user_info.json",
)