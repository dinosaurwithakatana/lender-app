package dev.dwak.lender.feature.item.navigation

import dev.dwak.lender.app.navigation.AuthenticatedLenderRoute
import dev.dwak.lender.lender_app.Parcelize
import kotlinx.serialization.Serializable

sealed interface ItemRoutes : AuthenticatedLenderRoute {
  @Parcelize
  @Serializable
  data object CreateItem: ItemRoutes
}