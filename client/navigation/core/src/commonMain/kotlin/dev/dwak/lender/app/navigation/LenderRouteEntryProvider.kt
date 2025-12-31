package dev.dwak.lender.app.navigation

import androidx.navigation3.runtime.EntryProviderScope

typealias LenderRouteEntryProvider = EntryProviderScope<LenderRoute>.(Navigator) -> Unit