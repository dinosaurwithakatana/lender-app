package dev.dwak.lender.app.navigation.core

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

typealias LenderRouteEntryProvider = EntryProviderScope<LenderRoute>.() -> Unit