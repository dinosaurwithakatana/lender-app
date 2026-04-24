package dev.dwak.lender.feature.home.presenter

sealed interface HomeEvents {
  data object Logout : HomeEvents
}