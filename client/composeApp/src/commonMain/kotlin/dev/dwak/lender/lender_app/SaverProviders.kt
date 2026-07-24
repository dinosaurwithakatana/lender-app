package dev.dwak.lender.lender_app

import androidx.savedstate.serialization.SavedStateConfiguration
import com.slack.circuit.runtime.screen.CircuitSaveable
import com.slack.circuit.runtime.screen.CircuitSaver
import com.slack.circuit.serialization.SerializableCircuitSaver
import dev.dwak.lender.feature.auth.navigation.api.AuthScreens
import dev.dwak.lender.feature.groups.navigation.GroupsScreens
import dev.dwak.lender.feature.home.navigation.HomeScreens
import dev.dwak.lender.feature.item.navigation.ItemScreens
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@ContributesTo(AppScope::class)
interface SaverProviders {

  @OptIn(ExperimentalSerializationApi::class)
  @Provides
  @SingleIn(AppScope::class)
  fun saver(): CircuitSaver = SerializableCircuitSaver(
    SavedStateConfiguration {
      serializersModule = SerializersModule {
        polymorphic(CircuitSaveable::class) {
          subclassesOfSealed<AuthScreens>()
          subclassesOfSealed<HomeScreens>()
          subclassesOfSealed<ItemScreens>()
          subclassesOfSealed<GroupsScreens>()
        }
      }
    }
  )

}