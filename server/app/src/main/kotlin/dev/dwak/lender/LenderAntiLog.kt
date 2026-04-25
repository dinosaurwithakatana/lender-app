package dev.dwak.lender

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.LogLevel
import io.ktor.server.application.Application
import org.slf4j.IMarkerFactory

@ContributesBinding(AppScope::class)
class LenderAntiLog(
  application: Application,
  private val markerFactory: IMarkerFactory
) : Antilog() {
  val environment = application.environment
  override fun performLog(
    priority: LogLevel,
    tag: String?,
    throwable: Throwable?,
    message: String?
  ) {
    val marker = markerFactory.getMarker(tag ?: "lender")
    with(environment.log) {
      when (priority) {
        LogLevel.ASSERT,
        LogLevel.VERBOSE,
        LogLevel.DEBUG -> debug(marker, message, throwable)
        LogLevel.INFO -> info(marker, message, throwable)
        LogLevel.WARNING -> warn(marker, message, throwable)
        LogLevel.ERROR -> error(marker, message, throwable)
      }
    }
  }
}