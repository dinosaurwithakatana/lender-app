package dev.dwak.lender.datastore.dev.dwak.lender.datastore

import androidx.datastore.core.Storage
import androidx.datastore.core.okio.OkioStorage
import dev.dwak.lender.datastore.DsServerConfig
import dev.dwak.lender.datastore.DsUserInfo
import kotlinx.io.files.Path
import okio.FileSystem
import okio.Path.Companion.toPath

actual fun createUserInfoStorage(appDir: Path): Storage<DsUserInfo> {
  return OkioStorage(
    serializer = UserInfoSerializer,
    fileSystem = FileSystem.SYSTEM,

    producePath = {
      "$appDir/user_info.json".toPath()
    })
}

actual fun createServerConfigStorage(appDir: Path): Storage<DsServerConfig> = OkioStorage(
  serializer = ServerConfigSerializer,
  fileSystem = FileSystem.SYSTEM,
  producePath = { "$appDir/server_config.json".toPath() },
)
