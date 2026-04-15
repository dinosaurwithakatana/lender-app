package dev.dwak.lender.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.command.main
import com.github.ajalt.clikt.core.subcommands
import dev.dwak.lender.lender_app.AppDir
import dev.dwak.lender.lender_app.DbDir
import dev.dwak.lender.lender_app.coroutines.Io
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import java.io.File


@DependencyGraph(AppScope::class)
interface CliGraph {
  val cli: suspend () -> Unit

  @Provides
  fun lenderCli(
    subCommands: Set<SuspendingCliktCommand>,
    args: Array<String>
  ): suspend () -> Unit = {
//    Napier.base(DebugAntilog())
    LenderCli()
      .subcommands(
        subCommands
          .sortedBy {
            it.commandName
          })
      .main(args)
  }

  @Provides
  @Io
  fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

  @Provides
  @AppDir
  fun appDirectory(): Path {
    val localStorageDir = Path("/data", "/cli")
    if(!SystemFileSystem.exists(localStorageDir)) {
      SystemFileSystem.createDirectories(localStorageDir, true)
    }
    return localStorageDir
  }

  @Provides
  @DbDir
  fun dbDirectory(): Path {
    val localStorageDir = Path("/data", "/data")
    if(!SystemFileSystem.exists(localStorageDir)) {
      SystemFileSystem.createDirectories(localStorageDir, true)
    }
    return localStorageDir
  }

  @DependencyGraph.Factory
  fun interface Factory {
    fun create(@Provides args: Array<String>): CliGraph
  }
}