import androidx.datastore.core.Storage
import dev.dwak.lender.datastore.DsUserInfo
import dev.zacsweers.metro.Provides

interface WebStorageProvider {
  @Provides
  fun storage(): Storage<DsUserInfo> {
  }
}