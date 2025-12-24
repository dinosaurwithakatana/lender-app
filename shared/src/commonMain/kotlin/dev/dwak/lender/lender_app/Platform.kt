package dev.dwak.lender.lender_app

interface Platform {
  val name: String
}

expect fun getPlatform(): Platform